package kr.pravusid.domain.user.exception;

public class InvalidUserException extends RuntimeException {

    public InvalidUserException() {
        super("부적합한 회원의 요청입니다");
    }

}
