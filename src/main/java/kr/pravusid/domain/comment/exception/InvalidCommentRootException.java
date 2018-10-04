package kr.pravusid.domain.comment.exception;

public class InvalidCommentRootException extends RuntimeException {

    public InvalidCommentRootException() {
        super("댓글의 댓글이 올바른 대상에게 요청되지 않았습니다");
    }

}
