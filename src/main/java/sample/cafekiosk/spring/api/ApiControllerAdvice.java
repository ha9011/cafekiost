package sample.cafekiosk.spring.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Object> bindException(MethodArgumentNotValidException e){
        System.out.println("-----bindException----");
        System.out.println(e.getBindingResult());
        String errorMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        return ApiResponse.of(HttpStatus.BAD_REQUEST, errorMessage, null);
    }

}
