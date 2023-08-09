package home.todayhome.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException{

    private Errorcode errorcode;
    private String message;

    public CustomException(Errorcode errorcode) {
        this.errorcode = errorcode;
        this.message = null;
    }

    public String getMessage() {
        if (message == null){
            return errorcode.getMessage();
        }
        return String.format("%s %s", errorcode.getMessage(), message);
    }

}
