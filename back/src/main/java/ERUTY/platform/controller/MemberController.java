package ERUTY.platform.controller;

import ERUTY.platform.domain.Member;
import ERUTY.platform.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());

        return "members/regist";
    }

    @PostMapping("/members/new")
    public String registration(@Valid MemberForm memberForm, BindingResult result) {
        memberService.validateConfirmPassword(memberForm);

        if(result.hasErrors()) {
            return "members/regist";
        }

        Member member = new Member(memberForm.getName(), memberForm.getEmail(), memberForm.getPassword());
        memberService.saveMember(member);

        return "redirect:/";
    }

    @GetMapping("/members/login")
    public String login_check(Model model) {
        model.addAttribute("memberLoginForm", new MemberLoginForm());

        return "members/loginsession";
    }

    @PostMapping("/members/login")
    public String login(@Valid MemberLoginForm memberLoginForm, HttpSession session, RedirectAttributes redirectAttributes) {
        Member loginMember = memberService.findLoginMember(memberLoginForm);

        session.setAttribute("loginId", loginMember.getId());

        /*
        // 로그인 전에 요청한 페이지가 있으면 그 페이지를 redirect
        String dest = (String) session.getAttribute("dest");
        String redirect = (dest == null) ? "/" : dest;

        return "redirect:" + redirect;
         */
        return "redirect:/";
    }

    @GetMapping("/members/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "redirect:/";
    }
}
