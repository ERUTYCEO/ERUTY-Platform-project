package ERUTY.platform.controller;

import ERUTY.platform.domain.Member;
import ERUTY.platform.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/mongo")
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/save")
    public String registration(@RequestParam String name, @RequestParam String email, @RequestParam String password, @RequestParam String confirmpassword) {

        Member member = new Member(name, email, password, confirmpassword);
        memberService.saveMember(member);

        return memberService.selectMember(member);
    }
}
