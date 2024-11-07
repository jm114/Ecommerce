package com.spring.ecommerce.order.infra.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.ecommerce.order.domain.Product;
import com.spring.ecommerce.order.domain.repository.ProductRepository;
import com.spring.ecommerce.order.infra.entity.ProductEntity;
import com.spring.ecommerce.order.infra.entity.ProductStockEntity;
import com.spring.ecommerce.order.infra.repository.ProductJpaRepository;
import com.spring.ecommerce.order.infra.repository.ProductStockJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final ProductStockJpaRepository productStockJpaRepository;
    private final EntityManager entityManager;


    @Override
    public List<Product> findAll() {

        List<ProductEntity> productInfos = productJpaRepository.findAll();
        if(productInfos.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> productIds = productInfos.stream()
                .map(ProductEntity::getId)
                .collect(Collectors.toList());

        List<ProductStockEntity> stockInfos = productStockJpaRepository.findByProductIdIn(productIds);


        return productInfos.stream()
                .map(products -> {
                    ProductStockEntity stockInfo = stockInfos.stream()
                            .filter(stock -> stock.getProductId().equals(products.getId()))
                            .findFirst()
                            .orElse(null);

                    return Product.builder()
                            .id(products.getId())
                            .name(products.getName())
                            .price(products.getPrice())
                            .stock(stockInfo != null ? stockInfo.getStock() : 0)  // ProductStock의 재고
                            .deleteYn(products.getDeleteYn())
                            .build();
                })
                .collect(Collectors.toList());


    }

    @Override
    public Product findById(Long productId) {

        ProductEntity productInfo = productJpaRepository.findById(productId).orElse(null);
        if(productInfo==null) {
            return null;
        }

        ProductStockEntity stockInfo = productStockJpaRepository.findById(productId).orElse(null);

        return Product.builder()
                .id(productInfo.getId())
                .name(productInfo.getName())
                .price(productInfo.getPrice())
                .stock(stockInfo != null ? stockInfo.getStock() : 0)  // 재고 정보
                .deleteYn(productInfo.getDeleteYn())
                .build();

    }

    @Override
    public Product updateProduct(Product productInfo) {

        ProductEntity savedProduct = productJpaRepository.save(productInfo.toEntity());

        ProductStockEntity savedStock = productStockJpaRepository.save(productInfo.toStockEntity());

        return savedProduct.toDomain(savedStock);


    }

    @Override
    public List<Product> findTopSelling(LocalDateTime startDate, LocalDateTime endDate, int limit) {
        //TODO: 쿼리 성능 체크하기
        String sql = "SELECT " +
                "p.id AS productId, " +
                "p.name AS productName, " +
                "COALESCE(SUM(oi.quantity), 0) AS totalQuantity " +
                "FROM products p " +
                "INNER JOIN order_items oi ON p.id = oi.product_id " +
                "INNER JOIN orders o ON oi.order_id = o.id AND o.status = 'COMPLETED'" +
                "WHERE o.created_at BETWEEN :startDate AND :endDate " +
                "GROUP BY p.id, p.name " +
                "ORDER BY totalQuantity DESC, p.id ASC " +
                "LIMIT :limit";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("limit", limit);

        List<Object[]> result = query.getResultList();

        return result.stream()
                .map(record -> {
                    Long productId = ((Number) record[0]).longValue();
                    String productName = (String) record[1];
                    int totalQuantity = ((Number) record[2]).intValue();

                    return Product.builder()
                            .id(productId)
                            .name(productName)
                            .totalQuantity(totalQuantity)
                            .build();
                })
                .collect(Collectors.toList());
    }

}


