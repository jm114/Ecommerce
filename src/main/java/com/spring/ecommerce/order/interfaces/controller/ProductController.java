package com.spring.ecommerce.order.interfaces.controller;

import com.spring.ecommerce.order.application.service.ProductService;
import com.spring.ecommerce.order.domain.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
@Tag(name = "Product", description = "상품 조회 API")
public class ProductController {

    private final ProductService productService;

    /**
     * - 상품 조회
     * - 인기 상품 조회
     */

    @GetMapping
    @Operation(summary = "상품 전체 조회", description = "전체 상품을 조회합니다.")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }


    @GetMapping("/popular")
    @Operation(summary = "인기 상품 조회", description = "5일간 가장 많이 팔린 상품을 조회합니다.")
    public ResponseEntity<List<Product>> getPopularProducts(@RequestParam(defaultValue = "3") int days,
                                                            @RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(productService.getPopularProducts(days, limit));

    }
}
