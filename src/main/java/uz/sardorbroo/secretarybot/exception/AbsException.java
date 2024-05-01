package uz.sardorbroo.secretarybot.exception;

import org.springframework.http.HttpStatus;

public class AbsException extends RuntimeException {

    private final String message;
    private final HttpStatus status;

    public AbsException(String message) {
        this.message = message;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public AbsException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

}
