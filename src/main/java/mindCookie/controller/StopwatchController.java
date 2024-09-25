package mindCookie.controller;

import lombok.RequiredArgsConstructor;
import mindCookie.domain.Member;
import mindCookie.dto.AllStopwatchDTO;
import mindCookie.dto.StateDTO;
import mindCookie.dto.StopwatchDTO;
import mindCookie.global.response.BaseResponse;
import mindCookie.global.response.BaseResponseCode;
import mindCookie.service.MemberService;
import mindCookie.service.StopwatchService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StopwatchController {
    private final MemberService memberService;
    private final StopwatchService stopwatchService;

    /**
     * 모든 날짜의 스탑워치 목록 가져오는 API
     * get : /stopwatch/all
     * @return
     */
    @ResponseBody
    @GetMapping("/api/stopwatch/all")
    public BaseResponse<List<AllStopwatchDTO>> getAllStopwatchData() {
        Member findMember = memberService.getMemberByUserName();

        List<AllStopwatchDTO> allStopwatches = stopwatchService.findAllStopwatchList(findMember);
        return new BaseResponse<>(allStopwatches, BaseResponseCode.SUCCESS);
    }
    /**
     * 오늘 스탑워치 가져오는 API
     * get : /member/{id}/stopwatch/today
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/api/stopwatch/today")
    public BaseResponse<List<StopwatchDTO>> getStopwatch(){
        Member findMember = memberService.getMemberByUserName();

        List<StopwatchDTO> stopwatches = stopwatchService.findTodayStopwatchList(findMember);
        return new BaseResponse<>(stopwatches, BaseResponseCode.SUCCESS);
    }

    /**
     * 오늘 스탑워치 업데이트 하는 API
     * @param id
     * @return
     */
    @ResponseBody
    @PutMapping("/api/stopwatch/update-time")
    public BaseResponse<StateDTO> stopStopwatch(@RequestBody StopwatchDTO stopwatchDTO){
        Member findMember = memberService.getMemberByUserName();

        stopwatchService.updateStopwatchTime(findMember, stopwatchDTO.getTarget(),stopwatchDTO.getTime());
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
    @PutMapping("/api/add-stopwatch-target")
    public BaseResponse<StateDTO> putStopwatchTarget(@RequestParam String add) {
        Member findMember = memberService.getMemberByUserName();

        stopwatchService.addStopwatchTarget(findMember, add);
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
    @PutMapping("/api/remove-stopwatch-target")
    public BaseResponse<StateDTO> removeStopwatchTarget(@RequestParam String targetToRemove) {
        Member findMember = memberService.getMemberByUserName();

        stopwatchService.removeStopwatchTarget(findMember, targetToRemove);
        return new BaseResponse<>(BaseResponseCode.SUCCESS_STOPWATCH_UPDATE);
    }
}
