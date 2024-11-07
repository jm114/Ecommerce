package com.spring.ecommerce.order.domain;

import com.spring.ecommerce.order.infra.entity.ProductEntity;
import com.spring.ecommerce.order.infra.entity.ProductStockEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    private Long id;
    private String name;
    private int price;
    private int stock;
    private String deleteYn;
    private int totalQuantity;

    public boolean checkStock(int quantity) {
        return this.stock >= quantity;
    }

    public ProductEntity toEntity() {
        return ProductEntity.builder()
                .name(this.name)
                .price(this.price)
                .restYn(this.stock == 0?"N":"Y")
                .build();
    }

    public ProductStockEntity toStockEntity(){
        return ProductStockEntity.builder()
                .productId(this.id)
                .stock(this.stock)
                .build();
    }

    public void deduct(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("주문 수량은 0보다 커야합니다.");
        }
        if (this.stock < quantity) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.stock -= quantity;
    }

    public void restore(int quantity) {
        this.stock += quantity;
    }

}
