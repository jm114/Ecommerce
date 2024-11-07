package com.spring.ecommerce.order.application.service;

import com.spring.ecommerce.order.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CartService {

    // 장바구니 조회 로직
    public List<Product> getCart(Long userId) {

        return new ArrayList<>();
    }

    // 장바구니에 상품 추가 로직
    public void addProductToCart(Long userId, Long productId, int quantity) {

    }

    // 장바구니에서 상품 삭제 로직
    public void removeProductFromCart(Long userId, Long productId) {

    }


}
