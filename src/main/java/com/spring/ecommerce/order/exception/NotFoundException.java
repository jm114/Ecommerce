package com.spring.ecommerce.order.exception;

public class NotFoundException extends RuntimeException{
    private NotFoundException(String message){
        super(message);
    }

    public static NotFoundException UserNotFoundException(Long userId){
        return new NotFoundException("사용자가 존재하지 않습니다. id: "+userId);
    }

    public static NotFoundException ProductNotFoundException(Long productId){
        return new NotFoundException("상품이 존재하지 않습니다. id: "+productId);
    }

    public static NotFoundException ProductAllNotFoundException(){
        return new NotFoundException("상품이 존재하지 않습니다.");
    }


}
