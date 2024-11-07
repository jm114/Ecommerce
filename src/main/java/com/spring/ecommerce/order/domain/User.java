package com.spring.ecommerce.order.domain;

import com.spring.ecommerce.order.infra.entity.UserBalanceEntity;
import com.spring.ecommerce.order.infra.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long id;
    private String name;
    private int balance;

    public void charge(int amount){
        if(amount <= 0 ){
            throw new IllegalArgumentException("충전금액은 0보다 커야합니다.");
        }
        this.balance += amount;
    }

    public boolean checkBalance(int price) {
        return this.balance >= price;
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                    .id(this.id)
                    .name(this.name)
                    .build();

    }

    public UserBalanceEntity toBalanceEntity(){
        return UserBalanceEntity.builder()
                .userId(this.id)
                .balance(this.balance)
                .build();
    }

    public void deduct(int totalPrice) {
        if (totalPrice <= 0) {
            throw new IllegalArgumentException("차감 금액은 0보다 커야합니다.");
        }
        if (this.balance < totalPrice) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }
        this.balance -= totalPrice;
    }

    public void restore(int totalPrice) {
        this.balance += totalPrice;
    }
}
