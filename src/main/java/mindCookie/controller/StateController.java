package mindCookie.controller;

import lombok.RequiredArgsConstructor;
import mindCookie.domain.Member;
import mindCookie.domain.State;
import mindCookie.dto.StateDTO;
import mindCookie.global.response.BaseResponse;
import mindCookie.global.response.BaseResponseCode;
import mindCookie.service.MemberService;
import mindCookie.service.StateService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class StateController {
    private final MemberService memberService;
    private final StateService stateService;
    @ResponseBody
    @GetMapping("/myState")
    public BaseResponse<StateDTO> getStateRequest(@RequestParam LocalDate date){
        Member findMember = memberService.getMemberByUserName();

        State todayState = stateService.findState(findMember.getId(), date);
        StateDTO returnValue = new StateDTO(todayState.getPositive(),todayState.getNegative(),todayState.getLifeSatisfaction(),todayState.getPhysicalConnection());
        return new BaseResponse<>(returnValue, BaseResponseCode.SUCCESS);
    }
    @ResponseBody
    @PutMapping("/myState")
    public BaseResponse<Void> putStateRequest(@RequestBody StateDTO stateDTO, @RequestParam LocalDate date){
        Member findMember = memberService.getMemberByUserName();

        stateService.updateOrCreateState(findMember, stateDTO, date);
        return new BaseResponse<>(BaseResponseCode.SUCCESS_STATE_UPDATE);
    }
}
