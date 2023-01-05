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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        log.info(memberForm.getName(), " + ", memberForm.getEmail(), " + ", memberForm.getConfirmpassword(), " + ", memberForm.getPassword());
        if(result.hasErrors()) {
            return "members/regist";
        }

        Member member = new Member(memberForm.getName(), memberForm.getEmail(), memberForm.getPassword());
        memberService.saveMember(member);

        return "redirect:/";
    }
}
