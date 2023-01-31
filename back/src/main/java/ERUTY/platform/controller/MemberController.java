package ERUTY.platform.controller;

import ERUTY.platform.domain.Member;
import ERUTY.platform.form.*;
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
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());

        return "signup";
    }

    @PostMapping("/members/new")
    public String registration(@Valid MemberForm memberForm, BindingResult result) {
        memberService.validateConfirmPassword(memberForm.getPassword(), memberForm.getConfirmpassword());

        // if(result.hasErrors()) {
        //     return "members/regist";
        // }

        Member member = new Member(memberForm.getName(), memberForm.getEmail(), memberForm.getPassword(), memberForm.isMarketingOk());

        memberService.saveMember(member);

        return "redirect:/";
    }

    @GetMapping("/members/login")
    public String login_check(Model model) {
        model.addAttribute("memberLoginForm", new MemberLoginForm());

        return "login";
    }

    @PostMapping("/members/login")
    public String login(@Valid MemberLoginForm memberLoginForm, HttpSession session, RedirectAttributes redirectAttributes) {
        Member loginMember = memberService.findLoginMember(memberLoginForm);

        session.setAttribute("loginId", loginMember.getId());
        log.info("session : " + session.getAttribute("loginId"));
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

    @GetMapping("/members/changepwd")
    public String changePwd(Model model) {
        model.addAttribute("changepwdForm", new changepwdForm());

        return "members/changepassword";
    }

    @PostMapping("/members/changepwd")
    public String changePassword(@Valid changepwdForm changepwdform, BindingResult result) {
        memberService.CheckAndUpdate(changepwdform);
        if (result.hasErrors()) {
            return "members/changepassword";
        }
        return "redirect:/";
    }

    @GetMapping("/members/findpwd")
    public String findPassword(Model model) {
        model.addAttribute("findPwdForm", new findPwdForm());

        return "members/findbyemail";
    }

    @PostMapping("/members/findpwd")
    public String change_Pwd_by_Email(@Valid findPwdForm pwdForm) {
        EmailForm emailForm = memberService.find_Email_by_Email_and_Name(pwdForm);
        memberService.sendEmail(emailForm);

        return "redirect:/members/login";
    }

    @GetMapping("/members/authmember")
    public String list(Model model) {
        List<Member> marketingList = memberService.getMarketingMember();

        model.addAttribute("memberList", marketingList);

        return "members/mange";
    }
}
