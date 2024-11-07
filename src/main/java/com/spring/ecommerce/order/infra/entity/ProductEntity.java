package com.spring.ecommerce.order.infra.entity;

import com.spring.ecommerce.order.domain.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;
    //private int stock;
    private String restYn;
    private String deleteYn;

    public Product toDomain(ProductStockEntity productStock){
        return Product.builder()
                .id(this.id)
                .name(this.name)
                .price(this.price)
                .stock(productStock.getStock()) //재고테이블의 재고 가져오기
                .deleteYn(this.deleteYn)
                .build();
    }
}
