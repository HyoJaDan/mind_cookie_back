package mindCookie.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BaseResponseCode {
    SUCCESS("200","요청에 성공하셨습니다", HttpStatus.OK.value()),
    SUCCESS_STATE("S01","State 업데이트에 성공했습니다.",HttpStatus.OK.value()),
    FAIL("400","요청하신 데이터가 없습니다", HttpStatus.BAD_REQUEST.value());
    private final String code;
    private final String message;
    private final int status;
    BaseResponseCode(String code, String message, int status) {
        this.code=code;
        this.message=message;
        this.status = status;
    }
}
