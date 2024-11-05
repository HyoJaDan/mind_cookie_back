package mindCookie.controller;

import lombok.RequiredArgsConstructor;
import mindCookie.domain.Member;
import mindCookie.dto.EventDTO;
import mindCookie.dto.EventInfoDTO;
import mindCookie.global.response.BaseResponse;
import mindCookie.global.response.BaseResponseCode;
import mindCookie.service.EventService;
import mindCookie.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    @GetMapping("/api/event-info")
    public BaseResponse<EventInfoDTO> getEventInfo(Authentication authentication){
        return new BaseResponse<>(memberService.getMemberEventInfo(authentication), BaseResponseCode.SUCCESS);
    }
    /**
     * - `GET` : 모든 날짜의 이벤트 리스트를 가져오는 API : `/api/all-event-list`
     *         - date : LocalDate
     *         - participants : List<String>
     *         - which_activity : String
     *         - emotion : String
     *         - emotion_rate : byte
     */
    @ResponseBody
    @GetMapping("/api/all-event-list")
    public BaseResponse<Map<LocalDate, List<EventDTO>>> getAllEventList() {
        Member findMember = memberService.getMemberByUserName();
        Map<LocalDate, List<EventDTO>> eventMap = eventService.getAllEventList(findMember.getId());
        return new BaseResponse<>(eventMap, BaseResponseCode.SUCCESS);
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
    @GetMapping("/api/event-list")
    public BaseResponse<List<EventDTO>> getEventList(@RequestParam LocalDate date){
        Member findMember = memberService.getMemberByUserName();

        List<EventDTO> returnValue= eventService.getEventListByDate(findMember.getId(), date);
        return new BaseResponse<>(returnValue, BaseResponseCode.SUCCESS);
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
    @PutMapping("/api/event")
    public BaseResponse<Void> PutEvent(@RequestBody EventDTO eventDTO){
        Member findMember = memberService.getMemberByUserName();

        eventService.addEvent(findMember, eventDTO);
        return new BaseResponse<>(BaseResponseCode.SUCCESS_PUT_EVENT);
    }
}
