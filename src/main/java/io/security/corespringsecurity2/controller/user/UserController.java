package io.security.corespringsecurity2.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping(value = "/mypage")
    public String myPage() {
        return "user/mypage";
    }
}