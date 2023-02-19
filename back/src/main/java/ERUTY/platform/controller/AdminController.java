package ERUTY.platform.controller;

import ERUTY.platform.domain.Member;
import ERUTY.platform.service.ItemService;
import ERUTY.platform.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/admin/authmember")
    public String managedList(Model model) {
        List<Member> marketingList = memberService.getMarketingMember();
        log.info("admin");
        model.addAttribute("memberList", marketingList);

        return "members/mange";
    }

    @GetMapping("/admin/pnm")
    public String pnumberList(Model model) {
        List<Member> pnumberList = memberService.getTotalPNumber();

        model.addAttribute("memberList", pnumberList);

        return "admin/pnumber";
    }
}
