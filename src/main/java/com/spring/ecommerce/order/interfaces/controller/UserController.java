package com.spring.ecommerce.order.interfaces.controller;


import com.spring.ecommerce.order.application.service.UserService;
import com.spring.ecommerce.order.domain.User;
import com.spring.ecommerce.order.interfaces.dto.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "사용자 잔액 충전/조회")
public class UserController {

    private final UserService userService;
    /**
     * - 잔액 조회
     * - 잔액 충전
     */

    @PostMapping("/charge")
    @Operation(summary = "잔액 충전", description = "사용자 잔액을 충전합니다.")
    public ResponseEntity<User> chargeBalance(@RequestBody UserRequest request) {
        User user = userService.chargeBalance(request.getUserId(), request.getAmount());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/balance")
    @Operation(summary = "잔액 조회", description = "사용자 잔액을 조회합니다.")
    public ResponseEntity<User> getBalance(@RequestParam Long userId) {
        User userInfo = userService.getUser(userId);
        return ResponseEntity.ok(userInfo);
    }
}
