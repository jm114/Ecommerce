package com.spring.ecommerce.order.infra.entity;

import com.spring.ecommerce.order.domain.User;
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
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    public User toDomain(UserBalanceEntity userBalance){
        return User.builder()
                .id(this.id)
                .name(this.name)
                .balance(userBalance.getBalance())  //잔액테이블에서 가져오기
                .build();
    }
}
