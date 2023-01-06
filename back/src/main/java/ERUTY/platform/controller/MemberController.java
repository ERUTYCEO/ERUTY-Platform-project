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

    @GetMapping("/member/login")
    public String login_check(Model model) {
        model.addAttribute("memberForm", new MemberForm());

        return "members/loginsession";
    }

    @PostMapping("/member/login")
    public String login(@Valid MemberLoginForm memberLoginForm, BindingResult result, HttpSession session) {
        Member loginMember = memberService.findLoginMember(memberLoginForm);

        session.setAttribute("memberId", loginMember.getId());
        session.setAttribute("memberEmail", loginMember.getEmail());

        return "redirect:/";
    }

    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "redirect:/";
    }
}
