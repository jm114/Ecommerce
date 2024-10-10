package com.spring.ecommerce.order.application.usecase;

import com.spring.ecommerce.order.application.service.OrderService;
import com.spring.ecommerce.order.application.service.UserService;
import com.spring.ecommerce.order.domain.Order;
import com.spring.ecommerce.order.domain.Product;
import com.spring.ecommerce.order.domain.User;
import com.spring.ecommerce.order.application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderServiceApplication {

    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;

    /**
     *
     * @param userId
     * @param productId
     * @param quantity
     * @return
     *
     * test는 한 번에 1개의 상품만 주문한다.
     */
    public Order order(Long userId, Long productId, Integer quantity) {

        User user  = userService.getUserById(userId);
        Product product = productService.getProductById(productId);

        //사용자 잔액 확인
        if (user.getBalance() < product.getPrice() * quantity) {
            throw new RuntimeException("Insufficient balance");
        }

        //상품 재고 확인
        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        //주문결제
        Order order = orderService.createOrder(user, product, quantity) ;

        // 재고 업데이트
        productService.updateProductStock(product, quantity);

        // 사용자 잔액 차감
        userService.updateUserBalance(user, product.getPrice() * quantity);

        return order;
    }

}
