package com.spring.ecommerce.order.application.service;

import com.spring.ecommerce.order.domain.User;
import com.spring.ecommerce.order.domain.repository.UserRepository;
import com.spring.ecommerce.order.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    /*
        예외사항 1. 유효하지 않은 사용자 v
                2. 충전 금액 음수일 때 v
                3. 동시성 문제(한 명의 사용자가 동시에 충전을 할 때 순서 보장)
     */

    //잔액충전
    public User chargeBalance(Long userId, int amount) {
        User user = userRepository.findById(userId);
        if (user == null) throw NotFoundException.UserNotFoundException(userId);

        user.charge(amount);
        return userRepository.updateUser(user);
    }

    //user 정보 조회
    public User getUser(Long userId){
        User user = userRepository.findById(userId);
        if (user == null) throw NotFoundException.UserNotFoundException(userId);
        return user;
    }

    //잔액 차감
    @Transactional
    public void deductBalance(User user, int totalPrice) {
        user.deduct(totalPrice);
        userRepository.updateUser(user);
    }

    //잔액 복구
    public void restoreBalance(User user, int totalPrice) {
        user.restore(totalPrice);
        userRepository.updateUser(user);
    }
}
