package mindCookie.controller;

import lombok.RequiredArgsConstructor;
import mindCookie.domain.*;
import mindCookie.dto.HobbitDTO;
import mindCookie.dto.PrimaryHobbitStatusDTO;
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
    @PostMapping("/add-hobbit")
    public BaseResponse<PrimaryHobbitStatusDTO> addHobbit(@RequestBody HobbitDTO hobbitDTO) {
        Member findMember = memberService.getMemberByUserName();
        PrimaryHobbitStatusDTO primaryHobbitStatusDTO = hobbitService.addHobbit(findMember, hobbitDTO.getPrimaryHobbit(), hobbitDTO.getHobbit(), hobbitDTO.getColor());

        if(primaryHobbitStatusDTO != null)
            return new BaseResponse<>(primaryHobbitStatusDTO, BaseResponseCode.SUCCESS);
        else
            return new BaseResponse<>(BaseResponseCode.FAIL);
    }

    //해당 날짜 모든 todo-list를 가져오기
    @ResponseBody
    @GetMapping("/today-status")
    public BaseResponse<List<PrimaryHobbitStatusDTO>> getTodayHobbitStatus(@RequestParam LocalDate date) {
        Member findMember = memberService.getMemberByUserName();
        List<PrimaryHobbitStatusDTO> hobbitStatusList = hobbitService.getTodayHobbitStatus(findMember, date);

        return new BaseResponse<>(hobbitStatusList, BaseResponseCode.SUCCESS);
    }
    @ResponseBody
    @PutMapping("/update-status/{primaryHobbitId}/{hobbitListId}")
    public BaseResponse<Boolean> updateTodayHobbitStatus(@PathVariable Long primaryHobbitId, @PathVariable Long hobbitListId,@RequestParam LocalDate date) {
        Member findMember = memberService.getMemberByUserName();
        boolean isDone = hobbitService.updateTodayHobbitStatus(findMember.getId(), primaryHobbitId, hobbitListId,date);

        return new BaseResponse<>(isDone, BaseResponseCode.SUCCESS);
    }

}
