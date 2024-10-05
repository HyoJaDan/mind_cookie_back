package mindCookie.controller;

import lombok.RequiredArgsConstructor;
import mindCookie.domain.*;
import mindCookie.dto.Hobbit.GetDTO.HobbitCombinedDTO;
import mindCookie.dto.Hobbit.ToDoDTO;
import mindCookie.dto.HobbitDTO;
import mindCookie.dto.Hobbit.PrimaryHobbitStatusDTO;
import mindCookie.global.response.BaseResponse;
import mindCookie.global.response.BaseResponseCode;
import mindCookie.service.HobbitService;
import mindCookie.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HobbitController {
    private final HobbitService hobbitService;
    private final MemberService memberService;
    // 목표 추가
    @ResponseBody
    @PostMapping("/api/add-hobbit")
    public BaseResponse<PrimaryHobbitStatusDTO> addHobbit(@RequestBody HobbitDTO hobbitDTO) {
        Member findMember = memberService.getMemberByUserName();
        PrimaryHobbitStatusDTO primaryHobbitStatusDTO = hobbitService.addHobbit(findMember, hobbitDTO.getPrimaryHobbit(), hobbitDTO.getHobbit(), hobbitDTO.getColor());

        if(primaryHobbitStatusDTO != null)
            return new BaseResponse<>(primaryHobbitStatusDTO, BaseResponseCode.SUCCESS);
        else
            return new BaseResponse<>(BaseResponseCode.FAIL);
    }

    // v1 - 해당 날짜 모든 todo-list를 가져오기
    @ResponseBody
    @GetMapping("/api/today-status")
    public BaseResponse<List<PrimaryHobbitStatusDTO>> getTodayHobbitStatus(@RequestParam LocalDate date) {
        Member findMember = memberService.getMemberByUserName();
        List<PrimaryHobbitStatusDTO> hobbitStatusList = hobbitService.getTodayHobbitStatus(findMember, date);

        return new BaseResponse<>(hobbitStatusList, BaseResponseCode.SUCCESS);
    }
    // v-2 금일 포함 7일간의 목표 상태 가져오기
    @ResponseBody
    @GetMapping("/api/week-status")
    public BaseResponse<List<ToDoDTO>> getHobbitStatusForNext7Days() {
        Member findMember = memberService.getMemberByUserName();
        List<ToDoDTO> hobbitStatusByDateList = hobbitService.getHobbitStatusForNext7Days(findMember);

        return new BaseResponse<>(hobbitStatusByDateList, BaseResponseCode.SUCCESS);
    }
    // v-3 기본 목표 정보와 날짜별 완료 상태를 함께 가져오기
    @GetMapping("/api/hobbit-status")
    public BaseResponse<HobbitCombinedDTO> getHobbitStatus() {
        Member findMember = memberService.getMemberByUserName();
        HobbitCombinedDTO hobbitCombinedDTO = hobbitService.getHobbitCombinedData(findMember);

        return new BaseResponse<>(hobbitCombinedDTO, BaseResponseCode.SUCCESS);
    }
    @ResponseBody
    @PutMapping("/api/update-status/{primaryHobbitId}/{hobbitListId}")
    public BaseResponse<Boolean> updateTodayHobbitStatus(@PathVariable Long primaryHobbitId, @PathVariable Long hobbitListId,@RequestParam LocalDate date) {
        Member findMember = memberService.getMemberByUserName();
        boolean isDone = hobbitService.updateTodayHobbitStatus(findMember.getId(), primaryHobbitId, hobbitListId,date);

        return new BaseResponse<>(isDone, BaseResponseCode.SUCCESS);
    }

    /**
     * hobbit 삭제 api
     * @param primaryHobbitId
     * @param hobbitId
     * @return
     */
    @ResponseBody
    @DeleteMapping("/api/delete-hobbit/{primaryHobbitId}/{hobbitId}")
    public BaseResponse<Boolean> deleteHobbit(@PathVariable Long primaryHobbitId, @PathVariable Long hobbitId) {
        boolean isDeleted = hobbitService.deleteHobbit(primaryHobbitId, hobbitId);

        return new BaseResponse<>(isDeleted, BaseResponseCode.SUCCESS);
    }
}
