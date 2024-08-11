package mindCookie.controller;

import lombok.RequiredArgsConstructor;
import mindCookie.domain.Member;
import mindCookie.global.response.BaseResponse;
import mindCookie.global.response.BaseResponseCode;
import mindCookie.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MeberController {
    private final MemberService memberService;


    @ResponseBody
    @GetMapping("api/member/{id}")
    public BaseResponse<Member> getMemberData(@PathVariable Long id){
        Member findMember = memberService.findOne(id);

        return new BaseResponse<>(findMember, BaseResponseCode.SUCCESS);
    }
}