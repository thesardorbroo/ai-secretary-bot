package uz.sardorbroo.secretarybot.exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends AbsException {

    private static final HttpStatus STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public InternalServerErrorException(String message) {
        super(message, STATUS);
    }

}
