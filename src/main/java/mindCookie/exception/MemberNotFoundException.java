package mindCookie.exception;

import mindCookie.global.response.BaseResponseCode;

public class MemberNotFoundException extends EntityNotFoundException {

    public MemberNotFoundException(){
        super(BaseResponseCode.FAIL);
    }
}
