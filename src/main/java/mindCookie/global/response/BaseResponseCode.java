package mindCookie.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BaseResponseCode {
    SUCCESS("200","요청에 성공하셨습니다", HttpStatus.OK.value());

    private final String code;
    private final String message;
    private final int status;
    BaseResponseCode(String code, String message, int status) {
        this.code=code;
        this.message=message;
        this.status = status;
    }
}
