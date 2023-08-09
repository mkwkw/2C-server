package home.todayhome.dto;

import home.todayhome.exception.Errorcode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {

    private String resultCode;
    private T result;

    public static <T> Response<T> success() {    // 서비스를 성공했는지만 확인
        return new Response<T>("SUCCESS", null);
    }

    public static <T> Response<T> success(T result) {   // 서비스를 성공하고 객체를 전달
        return new Response<T>("SUCCESS", result);
    }

    public static Response<Void> error(String resultCode) { // 실패 코드 반환
        return new Response<Void>(resultCode, null);
    }

    public static Response<String> error(Integer errorCode, String message) { // 실패 코드 반환
        return new Response<String>(String.valueOf(errorCode), message);
    }

    public String toStream() {
        if (result == null) {
            return "{" +
                    "\"resultCode\":"+ "\"" + resultCode + "\"," +
                    "\"result\":" + null + "}";
        }

        return "{" +
                "\"resultCode\":"+ "\"" + resultCode + "\"," +
                "\"result\":" + "\"" + result + "\"" + "}";
    }

}
