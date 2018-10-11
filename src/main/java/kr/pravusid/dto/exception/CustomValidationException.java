package kr.pravusid.dto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomValidationException extends RuntimeException {

    private FieldError error;

    public CustomValidationException(String objectName, String field, String defaultMessage) {
        this.error = new FieldError(objectName, field, defaultMessage);
    }

    public CustomValidationException(FieldError error) {
        this.error = error;
    }

    public FieldError getError() {
        return error;
    }

}
