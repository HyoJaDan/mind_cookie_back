package mindCookie.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BaseResponseCode {
    SUCCESS("200","요청에 성공하셨습니다", HttpStatus.OK.value()),
    SUCCESS_STATE_UPDATE("S01","State 업데이트에 성공했습니다.",HttpStatus.OK.value()),
    SUCCESS_STOPWATCH_UPDATE("S02","Stopwatch-list 업데이트에 성공했습니다.",HttpStatus.OK.value()),
    SUCCESS_PUT_EVENT("S03","Event 추가에 성공했습니다.",HttpStatus.OK.value()),
    FAIL("400","요청하신 데이터가 없습니다", HttpStatus.BAD_REQUEST.value()),
    MEMBER_NOT_FOUND("404","해당 사용자가 없습니다", HttpStatus.BAD_REQUEST.value());
    private final String code;
    private final String message;
    private final int status;
    BaseResponseCode(String code, String message, int status) {
        this.code=code;
        this.message=message;
        this.status = status;
    }
}
