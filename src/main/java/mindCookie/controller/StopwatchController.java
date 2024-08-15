package mindCookie.controller;

import lombok.RequiredArgsConstructor;
import mindCookie.dto.StateDTO;
import mindCookie.dto.StopwatchDTO;
import mindCookie.global.response.BaseResponse;
import mindCookie.global.response.BaseResponseCode;
import mindCookie.service.StopwatchService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @GetMapping("/member/{id}/stopwatch/today")
    public BaseResponse<List<StopwatchDTO>> getStopwatch(@PathVariable Long id){
        List<StopwatchDTO> stopwatches = stopwatchService.findTodayStopwatchList(id);
        return new BaseResponse<>(stopwatches, BaseResponseCode.SUCCESS);
    }

    /**
     * 스탑워치 리스트를 추가하는 api
     * PUT /member/1/add-stopwatch-target?add=target_name
     * @param id
     * @param add
     * @return
     */
    @ResponseBody
    @PutMapping("/member/{id}/add-stopwatch-target")
    public BaseResponse<StateDTO> putStopwatchTarget(
            @PathVariable Long id,
            @RequestParam String add) {
        stopwatchService.addStopwatchTarget(id,add);
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
    @PutMapping("/member/{id}/remove-stopwatch-target")
    public BaseResponse<StateDTO> removeStopwatchTarget(
            @PathVariable Long id,
            @RequestParam String targetToRemove) {
        stopwatchService.removeStopwatchTarget(id, targetToRemove);
        return new BaseResponse<>(BaseResponseCode.SUCCESS_STOPWATCH_UPDATE);
    }
}
