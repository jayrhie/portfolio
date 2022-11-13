package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.KakaoService;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoService kakaoService;
    private final MemberService memberService;

    @GetMapping("/do")
    public String loginPage() {
        return "kakao/login";
    }

    @GetMapping("/kakao")
    public String kakaoLogin(@RequestParam String code, Model model, HttpServletRequest request) throws IOException {
        log.warn("code = {}", code);
        String access_token = kakaoService.getToken(code);
        Map<String, Object> userInfo = kakaoService.getUserInfo(access_token);

        /*model.addAttribute("code", code);
        model.addAttribute("access_token", access_token);
        model.addAttribute("userInfo", userInfo);*/

        String nickname = (String) userInfo.get("nickname");
        Long kakaoId = Long.parseLong((String) userInfo.get("id"));

        Member kakaoMember = kakaoService.distinguishKakaoId(kakaoId);

        if (kakaoMember != null) {
            //로그인 성공 처리
            //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
            HttpSession session = request.getSession();
            //세션에 로그인 회원 정보 보관
            session.setAttribute("loginMember", kakaoMember);
            //세션 관리자를 통해 세션을 생성하고, 회원 데이터 보관
            return "redirect:/";
        }

        KakaoMemberForm newKakaoLoginMember = new KakaoMemberForm();

        newKakaoLoginMember.setName(nickname);
        newKakaoLoginMember.setKakaoId(kakaoId);

        model.addAttribute("kakao_temp_form", newKakaoLoginMember);

        return "kakao/createMemberForm";
    }

    @PostMapping("/kakao")
    public String kakaoMemberCreate(@Validated @ModelAttribute("kakao_temp_form") KakaoMemberForm form,
                                    BindingResult result) {

        if (result.hasErrors()) {
            return "kakao/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setKakaoId(form.getKakaoId());
        member.setName(form.getName());
        member.setPassword(form.getPassword());
        member.setAddress(address);

        log.warn("member = {}", member);

        memberService.join(member);
        return "redirect:/";
    }
}
