package ERUTY.platform.controller;

import ERUTY.platform.common.Messsage;
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
    public String registration(@Valid MemberForm memberForm, BindingResult result, Model model) {

        try {
            memberService.validateConfirmPassword(memberForm.getPassword(), memberForm.getConfirmpassword());

            if(result.hasErrors()) {
                model.addAttribute("data", new Messsage("모든 항목을 채워주십시오.", "/members/new"));

                return "message";
            }

            Member member = new Member(memberForm.getName(), memberForm.getEmail(), memberForm.getPassword(), memberForm.isMarketingOk());

            memberService.saveMember(member);
        } catch (IllegalStateException exception) {
            model.addAttribute("data", new Messsage(exception.getMessage(), "/members/new"));

            return "message";
        }

        model.addAttribute("data", new Messsage("성공적으로 회원가입이 되셨습니다.", "/"));

        return "message";
        //return "redirect:/";
    }

    @GetMapping("/members/login")
    public String login_check(Model model) {
        model.addAttribute("memberLoginForm", new MemberLoginForm());

        return "login";
    }

    @PostMapping("/members/login")
    public String login(@Valid MemberLoginForm memberLoginForm, BindingResult result, Model model, HttpSession session) {
        try {
            Member loginMember = memberService.findLoginMember(memberLoginForm);

            if(result.hasErrors()) {
                model.addAttribute("data", new Messsage("모든 항목을 채워주십시오.", "/members/login"));

                return "message";
            }

            session.setAttribute("loginId", loginMember.getId());
            log.info("session : " + session.getAttribute("loginId"));

        } catch (IllegalStateException exception) {
            log.info("exception : " + exception.getMessage());

            model.addAttribute("data", new Messsage(exception.getMessage(), "/members/login"));

            return "message";
        }

        // 로그인 전에 요청한 페이지가 있으면 그 페이지를 redirect
        String dest = (String) session.getAttribute("dest");
        String redirect = (dest == null) ? "/" : dest;

        model.addAttribute("data", new Messsage("로그인 되었습니다.", redirect));

        //return "redirect:" + redirect;

        return "message";
    }

    @GetMapping("/members/logout")
    public String logout(Model model, HttpSession session) {
        session.invalidate();
        model.addAttribute("data", new Messsage("로그아웃 되었습니다.", "/"));

        return "message";
        //return "redirect:/";
    }

    @GetMapping("/members/changepwd")
    public String changePwd(Model model) {
        model.addAttribute("changepwdForm", new changepwdForm());

        return "members/changepassword";
    }

    @PostMapping("/members/changepwd")
    public String changePassword(@Valid changepwdForm changepwdform, BindingResult result,Model model) {
        try {
            memberService.CheckAndUpdate(changepwdform);

            if (result.hasErrors()) {
                model.addAttribute("data", new Messsage("모든 항목을 채워주십시오.", "/members/changepwd"));

                return "message";
            }

        } catch (IllegalStateException exception) {
            model.addAttribute("data", new Messsage(exception.getMessage(), "/members/changepwd"));

            return "message";
        }
        model.addAttribute("data", new Messsage("비밀번호 변경을 완료하였습니다.", "/"));

        return "message";
        //return "redirect:/";
    }

    @GetMapping("/members/findpwd")
    public String findPassword(Model model) {
        model.addAttribute("findPwdForm", new findPwdForm());

        return "members/findbyemail";
    }

    @PostMapping("/members/findpwd")
    public String change_Pwd_by_Email(@Valid findPwdForm pwdForm, Model model) {
        try {
            EmailForm emailForm = memberService.find_Email_by_Email_and_Name(pwdForm);
            memberService.sendEmail(emailForm);
        } catch (IllegalStateException exception) {
            model.addAttribute("data", new Messsage(exception.getMessage(), "/members/findpwd"));

            return "message";
        }
        model.addAttribute("data", new Messsage("로그인 페이지로 이동합니다", "/members/login"));

        return "message";
        //return "redirect:/members/login";
    }

    @GetMapping("/members/authmember")
    public String list(Model model) {
        List<Member> marketingList = memberService.getMarketingMember();

        model.addAttribute("memberList", marketingList);

        return "members/mange";
    }
}
