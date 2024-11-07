package com.spring.ecommerce.order.domain.repository;

import com.spring.ecommerce.order.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

public interface UserRepository {
    User findById(Long userId) ;
    User updateUser(User user) ;
}
