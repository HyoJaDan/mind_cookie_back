package mindCookie.controller;

import lombok.RequiredArgsConstructor;
import mindCookie.domain.Member;
import mindCookie.domain.State;
import mindCookie.dto.StateDTO;
import mindCookie.global.response.BaseResponse;
import mindCookie.global.response.BaseResponseCode;
import mindCookie.service.MemberService;
import mindCookie.service.StateService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StateController {
    private final MemberService memberService;
    private final StateService stateService;
    @ResponseBody
    @GetMapping("/myState")
    public BaseResponse<StateDTO> getStateRequest(){
        Member findMember = memberService.getMemberByUserName();
        State todayState = stateService.findTodayState(findMember.getId());
        StateDTO returnValue = new StateDTO(todayState.getPositive(),todayState.getNegative(),todayState.getLifeSatisfaction(),todayState.getPhysicalConnection());
        return new BaseResponse<>(returnValue, BaseResponseCode.SUCCESS);
    }
    @ResponseBody
    @PutMapping("/myState")
    public BaseResponse<State> putStateRequest(Authentication authentication, @RequestBody StateDTO stateDTO){
        stateService.updateOrCreateState(authentication, stateDTO);
        return new BaseResponse<>(BaseResponseCode.SUCCESS_STATE_UPDATE);
    }
}
