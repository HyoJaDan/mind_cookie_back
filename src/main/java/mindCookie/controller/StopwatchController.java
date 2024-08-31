package mindCookie.controller;

import lombok.RequiredArgsConstructor;
import mindCookie.dto.StateDTO;
import mindCookie.dto.StopwatchDTO;
import mindCookie.global.response.BaseResponse;
import mindCookie.global.response.BaseResponseCode;
import mindCookie.service.StopwatchService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StopwatchController {
    private final StopwatchService stopwatchService;

    /**
     * 오늘 스탑워치 가져오는 API
     * get : /member/{id}/stopwatch/today
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/stopwatch/today")
    public BaseResponse<List<StopwatchDTO>> getStopwatch(Authentication authentication){
        List<StopwatchDTO> stopwatches = stopwatchService.findTodayStopwatchList(authentication);
        return new BaseResponse<>(stopwatches, BaseResponseCode.SUCCESS);
    }

    /**
     * 오늘 스탑워치 업데이트 하는 API
     * @param id
     * @return
     */
    @ResponseBody
    @PutMapping("/stopwatch/update-time")
    public BaseResponse<StateDTO> stopStopwatch(Authentication authentication, @RequestBody StopwatchDTO stopwatchDTO){
        stopwatchService.updateStopwatchTime(authentication, stopwatchDTO.getTarget(),stopwatchDTO.getTime());
        return new BaseResponse<>(BaseResponseCode.SUCCESS_STOPWATCH_UPDATE);
    }
    /**
     * 스탑워치 리스트를 추가하는 api
     * PUT /member/1/add-stopwatch-target?add=target_name
     * @param id
     * @param add
     * @return
     */
    @ResponseBody
    @PutMapping("/add-stopwatch-target")
    public BaseResponse<StateDTO> putStopwatchTarget(
            Authentication authentication,
            @RequestParam String add) {
        stopwatchService.addStopwatchTarget(authentication,add);
        return new BaseResponse<>(BaseResponseCode.SUCCESS_STOPWATCH_UPDATE);
    }
    /**
     * 스탑워치 리스트를 삭제하는 api
     * PUT /member/1/remove-stopwatch-target?targetToRemove=target_name
     * @param id
     * @param add
     * @return
     */
    @ResponseBody
    @PutMapping("/remove-stopwatch-target")
    public BaseResponse<StateDTO> removeStopwatchTarget(
            Authentication authentication,
            @RequestParam String targetToRemove) {
        stopwatchService.removeStopwatchTarget(authentication, targetToRemove);
        return new BaseResponse<>(BaseResponseCode.SUCCESS_STOPWATCH_UPDATE);
    }
}
