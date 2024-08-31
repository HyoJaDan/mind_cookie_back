package mindCookie.controller;

import lombok.RequiredArgsConstructor;
import mindCookie.domain.Member;
import mindCookie.dto.MemberDTO;
import mindCookie.global.response.BaseResponse;
import mindCookie.global.response.BaseResponseCode;
import mindCookie.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @ResponseBody
    @GetMapping("/member")
    public BaseResponse<MemberDTO> getMember(Authentication authentication){
        MemberDTO findMember = memberService.getMemberByUserName(authentication);
        return new BaseResponse<>(findMember, BaseResponseCode.SUCCESS);
    }
    @ResponseBody
    @GetMapping("/member/{id}")
    public BaseResponse<Member> getMemberData(@PathVariable Long id){
        Member findMember = memberService.getMemberById(id);

        return new BaseResponse<>(findMember, BaseResponseCode.SUCCESS);
    }
}
