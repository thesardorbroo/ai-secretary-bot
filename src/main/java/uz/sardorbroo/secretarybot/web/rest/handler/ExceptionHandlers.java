package uz.sardorbroo.secretarybot.web.rest.handler;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import uz.sardorbroo.secretarybot.exception.AbsException;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(AbsException.class)
    public ResponseEntity<ErrorResponseDTO> handleCustomExceptions(AbsException exception, WebRequest request) {

        ErrorResponseDTO error = new ErrorResponseDTO()
                .setMessage(exception.getMessage())
                .setCode(exception.getStatus().name());

        return new ResponseEntity<>(error, exception.getStatus());
    }

    @Data
    @Accessors(chain = true)
    private static class ErrorResponseDTO {

        private String message;

        private String code;

    }

}
