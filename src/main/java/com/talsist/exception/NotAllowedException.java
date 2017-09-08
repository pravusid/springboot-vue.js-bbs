package com.talsist.exception;

public class NotAllowedException extends RuntimeException {

    @Override
    public String getMessage() {
        return "권한이 없습니다";
    }

}
