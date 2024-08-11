package mindCookie.exception;

import mindCookie.global.response.BaseResponseCode;

public class StateNotFoundException extends EntityNotFoundException{
    public StateNotFoundException(){
        super(BaseResponseCode.FAIL);
    }
}
