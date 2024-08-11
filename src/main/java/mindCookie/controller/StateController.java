package mindCookie.controller;

import lombok.RequiredArgsConstructor;
import mindCookie.domain.State;
import mindCookie.dto.StateDTO;
import mindCookie.global.response.BaseResponse;
import mindCookie.global.response.BaseResponseCode;
import mindCookie.service.StateService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StateController {
    private final StateService stateService;

    @ResponseBody
    @GetMapping("api/member/{id}/myState")
    public BaseResponse<StateDTO> getStateRequest(@PathVariable Long id){
        State todayState = stateService.findTodayState(id);
        StateDTO returnValue = new StateDTO(todayState.getPositive(),todayState.getNegative(),todayState.getLifeSatisfaction(),todayState.getPhysicalConnection());
        return new BaseResponse<>(returnValue, BaseResponseCode.SUCCESS);
    }
    @ResponseBody
    @PutMapping("api/member/{id}/myState")
    public BaseResponse<State> putStateRequest(@PathVariable Long id, @RequestBody StateDTO stateDTO){
        State updatedState = stateService.updateOrCreateState(id, stateDTO);
        return new BaseResponse<>(updatedState, BaseResponseCode.SUCCESS);
    }
}
