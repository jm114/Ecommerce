package com.spring.ecommerce.order.application.usecase;

import com.spring.ecommerce.order.application.service.*;
import com.spring.ecommerce.order.domain.*;
import com.spring.ecommerce.order.interfaces.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RequiredArgsConstructor
@Component
@Slf4j
public class OrderFacade {

    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;
    private final PaymentService paymentService;
    private final DataMessageService messageService;

    private final RedissonClient redissonClient;


    public Order order(OrderRequest request) throws InterruptedException {
        Order order = request.convertToOrder();
        User user = userService.getUser(order.getUserId());
        Map<Long, Product> products = productService.getProductById(order.getOrderItem());
        int totalPrice = orderService.calculateTotalPrice(order.getOrderItem(), products);

        //재고와 user 잔액 검증
        validateOrder(user, totalPrice, products, order.getOrderItem());

        RLock userLock = redissonClient.getLock("lock:user:" + user.getId());
        RLock productLock = redissonClient.getLock("lock:product:" + order.getOrderItem().keySet());

        try {

            if(!userLock.tryLock(10, 5, TimeUnit.SECONDS) || !productLock.tryLock(10, 5, TimeUnit.SECONDS)) {
                throw new TimeoutException("락을 획득할 수 없습니다.");
            }

            // 재고/잔액 차감
            productService.deductStock(products, order.getOrderItem());
            userService.deductBalance(user, totalPrice);

            // 결제 처리
            Payment paymentResult = paymentService.executePayment(order);

            if (paymentResult.getPaymentStatus() == PaymentStatus.COMPLETED) {
                Order completedOrder = orderService.createOrder(order, products); //주문 저장
                messageService.sendOrderData(user.getId(), completedOrder); //주문정보 전송(외부 데이터 플랫폼에)
                return completedOrder;
            } else {
                productService.restoreStock(products, order.getOrderItem()); //재고 원복
                userService.restoreBalance(user, totalPrice);   //잔액 원복
                throw new IllegalArgumentException("결제 실패로 인해 주문이 취소되었습니다.");
            }

        }catch (Exception e){
            log.error("주문 처리 중 오류 발생: {}", e.getMessage());
            throw new IllegalArgumentException("주문 중 오류가 발생하여 취소되었습니다.", e);
        } finally {
            userLock.unlock();
            productLock.unlock();
        }
    }


    private void validateOrder(User user, int totalPrice, Map<Long, Product> products, Map<Long, Integer> items) {
        // 잔액 확인
        if (!user.checkBalance(totalPrice)) {
            throw new IllegalArgumentException("잔액이 부족하여 주문할 수 없습니다.");
        }

        // 재고 확인
        for (Map.Entry<Long, Integer> entry : items.entrySet()) {
            Long productId = entry.getKey();
            int quantity = entry.getValue();

            Product product = products.get(productId);
            if (product == null || !product.checkStock(quantity)) {
                throw new IllegalArgumentException("상품 " + productId + "의 재고가 부족합니다.");
            }
        }
    }

}
