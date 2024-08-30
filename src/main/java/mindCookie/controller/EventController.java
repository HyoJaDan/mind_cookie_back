package mindCookie.controller;

import lombok.RequiredArgsConstructor;
import mindCookie.dto.EventDTO;
import mindCookie.dto.EventInfoDTO;
import mindCookie.global.response.BaseResponse;
import mindCookie.global.response.BaseResponseCode;
import mindCookie.service.EventService;
import mindCookie.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final MemberService memberService;
    /**
     * - `GET` : 내 event 정보 가져오는 API : `/api/member/{id}/eventInfo`
     *     - success :
     *         - event_participants : List<String>
     *         - event_activities : List<String>
     *         - event_emotions : List<String>
     *     - fail :
     *         - 데이터가 없습니다.
     *         - 기타 오류
     */
    @ResponseBody
    @GetMapping("member/{id}/event-info")
    public BaseResponse<EventInfoDTO> getEventInfo(@PathVariable Long id){
        return new BaseResponse<>(memberService.getMemberEventInfo(id), BaseResponseCode.SUCCESS);
    }

    /**
     * - `GET` : 금일 event List 가져오는 API : `member/{id}/eventList`
     *         - date : LocalDateTime
     *         - participants : List<String>
     *         - which-activity : String
     *         - emotion : string
     *         - emotion_rate : byte
     */
    @ResponseBody
    @GetMapping("member/{id}/event-list")
    public BaseResponse<List<EventDTO>> getEventList(@PathVariable Long id){
         return new BaseResponse<>(eventService.getEventListByDate(id),BaseResponseCode.SUCCESS);
    }
    /**
     * - `PUT` : 금일 event List를 추가하는 API : `/api/member/{id}/event`
     *     - `@PathVariable Long id,`
     *     - `@RequestBody`
     *         - date : LocalDateTime
     *         - participants : List<String>
     *         - which-activity : String
     *         - emotion : string
     *         - emotion_rate : byte
     */
    @ResponseBody
    @PutMapping("member/{id}/event")
    public BaseResponse<Void> PutEvent(@PathVariable Long id, @RequestBody EventDTO eventDTO){
        eventService.addEvent(id, eventDTO);
        return new BaseResponse<>(BaseResponseCode.SUCCESS_PUT_EVENT);
    }
}
