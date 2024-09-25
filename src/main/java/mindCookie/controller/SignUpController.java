package mindCookie.controller;

import lombok.RequiredArgsConstructor;
import mindCookie.dto.SignUpDTO;
import mindCookie.service.SignUpService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class SignUpController {
    private final SignUpService signUpService;

    @PostMapping("/join")
    public String signUpProcess(@RequestBody SignUpDTO signUpDTO) {
        boolean success = signUpService.signUpProcess(signUpDTO);
        if (success) {
            return "회원가입 성공";
        } else {
            return "회원가입 실패";
        }
    }
}
