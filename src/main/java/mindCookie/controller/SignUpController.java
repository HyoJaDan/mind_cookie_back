package mindCookie.controller;

import lombok.RequiredArgsConstructor;
import mindCookie.domain.Member;
import mindCookie.dto.SignUpDTO;
import mindCookie.service.MemberService;
import mindCookie.service.SignUpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
class SignUpController {
    private final SignUpService signUpService;
    private final MemberService memberService;

    @PostMapping("/join")
    public boolean signUpProcess(@RequestBody SignUpDTO signUpDTO) {
        boolean success = signUpService.signUpProcess(signUpDTO);
        if (success) {
            return true;
        } else {
            return false;
        }
    }
    @DeleteMapping("/member/delete")
    public boolean deleteMember() {
        Member findMember = memberService.getMemberByUserName();
        boolean isDeleted = signUpService.deleteMember(findMember);
        if (isDeleted) return true;
        else return false;
    }
}
