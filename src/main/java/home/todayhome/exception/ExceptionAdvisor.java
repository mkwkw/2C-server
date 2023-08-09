package home.todayhome.exception;

import home.todayhome.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvisor {

    @ExceptionHandler(CustomException.class)
    public Response<?> errorResponse(CustomException e) {
        return Response.error(e.getErrorcode().getStatus().value(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public Response<?> runtimeExceptionResponse(RuntimeException e) {
        return Response.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
