package mindCookie.controller;

import lombok.RequiredArgsConstructor;
import mindCookie.domain.Member;
import mindCookie.domain.State;
import mindCookie.dto.AllStateDTO;
import mindCookie.dto.StateDTO;
import mindCookie.global.response.BaseResponse;
import mindCookie.global.response.BaseResponseCode;
import mindCookie.service.MemberService;
import mindCookie.service.StateService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    /**
     * - `GET` : 사용자의 모든 state 정보를 배열로 반환하는 API : `/api/member/state`
     *     - success :
     *         - state 배열을 반환
     *     - fail :
     *         - 데이터가 없습니다.
     *         - 기타 오류
     */
    @GetMapping("/all-state")
    public BaseResponse<List<AllStateDTO>> getAllStates() {
        Long memberId = memberService.getMemberByUserName().getId();

        List<AllStateDTO> states = stateService.getAllStates(memberId);
        return new BaseResponse<>(states, BaseResponseCode.SUCCESS);
    }
}
