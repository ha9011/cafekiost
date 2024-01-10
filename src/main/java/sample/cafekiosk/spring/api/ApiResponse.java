package sample.cafekiosk.spring.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Getter
@NoArgsConstructor
public class ApiResponse<T> {

    private int code;
    private HttpStatus httpStatus;
    private String message;
    private T data;


    public ApiResponse( HttpStatus httpStatus, String message, T data) {
        this.code = httpStatus.value();
        this.httpStatus = httpStatus;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus, T data){
        return new ApiResponse<>(httpStatus, httpStatus.name(), data);
    }
    public static <T> ApiResponse<T> of(HttpStatus httpStatus, String message, T data){
        return new ApiResponse<>(httpStatus, message, data);
    }
    public static <T> ApiResponse<T> ok(T data){
        System.out.println("---ApiResponse-");
        System.out.println(data);
        return of(HttpStatus.OK, data);
    }
}
