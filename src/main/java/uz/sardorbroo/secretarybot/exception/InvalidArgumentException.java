package uz.sardorbroo.secretarybot.exception;

import org.springframework.http.HttpStatus;

public class InvalidArgumentException extends AbsException {

    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public InvalidArgumentException(String message) {
        super(message, STATUS);
    }

}
