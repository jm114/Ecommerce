package com.spring.ecommerce.order.application.service;

import com.spring.ecommerce.order.domain.Product;
import com.spring.ecommerce.order.domain.repository.ProductRepository;
import com.spring.ecommerce.order.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    /*
        예외사항 1. 유효하지 않은 상품
               2. 조회 시점과 실제 재고 간 차이가 없도록 해야함..
               3. 동시성 문제(재고 차감할 때 순서보장되도록)
     */

    //상품 전체 조회
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if(products.isEmpty()) throw NotFoundException.ProductAllNotFoundException();
        return products;
    }
    
    //상품 조회
    public Map<Long, Product> getProductById(Map<Long, Integer> items) {
        Map<Long, Product> productInfos = new HashMap<>();

        for(Long productId : items.keySet()){
            Product product = productRepository.findById(productId);
            if(product != null){
                productInfos.put(productId, product);
            }else{
                throw NotFoundException.ProductNotFoundException(productId);
            }
        }
        return productInfos;
    }

    //재고 차감
    @Transactional
    public void deductStock(Map<Long, Product> products, Map<Long, Integer> items) {
        for(Map.Entry<Long, Integer> item: items.entrySet()){
            Long productId = item.getKey();
            Integer quantity = item.getValue();
            Product product = products.get(productId);

            if(product != null) {
                product.deduct(quantity);
                productRepository.updateProduct(product);
            }
        }
    }

    //재고 복구
    public void restoreStock(Map<Long, Product> products, Map<Long, Integer> items) {
        for(Map.Entry<Long, Integer> item: items.entrySet()){
            Long productId = item.getKey();
            Integer quantity = item.getValue();
            Product product = products.get(productId);

            if(product != null) {
                product.restore(quantity);
                productRepository.updateProduct(product);
            }
        }
    }


    @Cacheable(value = "popularProducts",   key = "'popular:' + #days + ':' + #limit")
    public List<Product> getPopularProducts(int days, int limit) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(days);

        return productRepository.findTopSelling(startDate, endDate, limit);
    }


    @Scheduled(cron = "3 0 0 * * ?")
    @CacheEvict(value = "popularProducts", allEntries = true)
    public void evictPopularProductsCache() {
        this.getPopularProducts(3, 5);
    }



}
