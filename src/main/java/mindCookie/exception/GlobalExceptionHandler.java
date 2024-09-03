package mindCookie.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import mindCookie.global.response.BaseResponse;
import mindCookie.global.response.BaseResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<BaseResponse<Void>> handleMemberNotFoundException(MemberNotFoundException ex) {
        BaseResponse<Void> response = new BaseResponse<>(BaseResponseCode.FAIL);
        return ResponseEntity.status(BaseResponseCode.FAIL.getStatus()).body(response);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<BaseResponse> handleEntityNotFoundException(EntityNotFoundException e,
                                                                      HttpServletRequest request) {
        log.error("[ERROR] : {}", e.getMessage(), e);

        BaseResponse baseResponse = new BaseResponse(e.getBaseResponseCode());

        return new ResponseEntity<>(baseResponse, HttpStatus.valueOf(e.getBaseResponseCode().getStatus()));
    }

}
