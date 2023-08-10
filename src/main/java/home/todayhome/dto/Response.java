package home.todayhome.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {
    
    private Boolean result;
    private Integer status;
    private T data;

    public static <T> Response<T> success() {    // 서비스를 성공했는지만 확인
        return new Response<T>(true, 200, null);
    }

    public static <T> Response<T> success(T result) {   // 서비스를 성공하고 객체를 전달
        return new Response<T>(true, 200, result);
    }

    public static <T> Response<T> error(T resultCode) { // 실패 코드 반환
        return new Response<T>(false, 404, resultCode);
    }

    public static Response<String> error(Integer errorCode, String message) { // 실패 코드 반환
        return new Response<String>(false, errorCode, message);
    }

    public String toStream() {
        if (data == null) {
            return "{" +
                    "\"resultCode\":"+ "\"" + status + "\"," +
                    "\"result\":" + null + "}";
        }

        return "{" +
                "\"resultCode\":"+ "\"" + status + "\"," +
                "\"result\":" + "\"" + data + "\"" + "}";
    }

}
