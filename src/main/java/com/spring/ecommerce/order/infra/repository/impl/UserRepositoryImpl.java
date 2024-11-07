package com.spring.ecommerce.order.infra.repository.impl;

import com.spring.ecommerce.order.domain.User;
import com.spring.ecommerce.order.domain.repository.UserRepository;
import com.spring.ecommerce.order.infra.entity.UserBalanceEntity;
import com.spring.ecommerce.order.infra.entity.UserEntity;
import com.spring.ecommerce.order.infra.repository.UserBalanceJpaRepository;
import com.spring.ecommerce.order.infra.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserBalanceJpaRepository userBalanceJpaRepository;

    @Override
    public User findById(Long userId) {
        UserEntity userInfo = userJpaRepository.findById(userId).orElse(null);
        if(userInfo==null){
            return null;
        }

        UserBalanceEntity userBalanceEntity = userBalanceJpaRepository.findByUserId(userId);
        int balance = userBalanceEntity != null ? userBalanceEntity.getBalance() : 0;

        return User.builder()
                .id(userInfo.getId())
                .name(userInfo.getName())
                .balance(balance) // 잔액 설정
                .build();
    }

    @Override
    public User updateUser(User user) {
        //사용자테이블 update
        UserEntity savedUser = userJpaRepository.save(user.toEntity());

        //잔액테이블 update
        UserBalanceEntity savedBalance = userBalanceJpaRepository.save(user.toBalanceEntity());

        return savedUser.toDomain(savedBalance);
    }
}


