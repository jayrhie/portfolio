package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    @GetMapping("/login")
    public String loginForm(@ModelAttribute("form") LoginForm form) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(/*@Validated*/ @ModelAttribute("form") LoginForm form,
                        HttpServletRequest request, BindingResult result,
                        @RequestParam(defaultValue = "/") String redirectURL) {

        log.warn(result.toString());

        if (result.hasErrors()) {
            log.info("login element error");
            return "/login/loginForm";
        }

        log.warn(result.toString());

        Member loginMember = loginService.login(form.getName(), form.getPassword());

        if (loginMember == null) {
            result.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "/login/loginForm";
        }

        //로그인 성공 처리
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute("loginMember", loginMember);
        //세션 관리자를 통해 세션을 생성하고, 회원 데이터 보관
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
