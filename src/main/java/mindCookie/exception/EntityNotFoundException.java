package mindCookie.exception;

import lombok.Getter;
import mindCookie.global.response.BaseResponseCode;

@Getter
public class EntityNotFoundException extends RuntimeException{
    private final BaseResponseCode baseResponseCode;
    public EntityNotFoundException(BaseResponseCode baseResponseCode){
        super(baseResponseCode.FAIL.getMessage());
        this.baseResponseCode = baseResponseCode;
    }
}
