package ERUTY.platform.controller;

import ERUTY.platform.common.Messsage;
import ERUTY.platform.domain.Item;
import ERUTY.platform.domain.Member;
import ERUTY.platform.form.*;
import ERUTY.platform.service.EmailService;
import ERUTY.platform.service.ItemService;
import ERUTY.platform.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final ItemService itemService;
    private final EmailService emailService;

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

            Member member = new Member(memberForm.getName(),
                    memberForm.getEmail(),
                    memberForm.getPassword(),
                    memberForm.getPhoneNumber(),
                    memberForm.isMarketingOk());

            memberService.saveMember(member);

        } catch (IllegalStateException exception) {
            model.addAttribute("data", new Messsage(exception.getMessage(), "/members/new"));

            return "message";
        }

        model.addAttribute("data", new Messsage("성공적으로 회원가입이 되셨습니다.", "/"));

        return "message";
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
            /*
            if(loginMember.getEmail().equals("kimue7057@pusan.ac.kr")) {
                session.setAttribute("admin", loginMember.getId());
            }
             */
            if(loginMember.getEmail().equals("kimue7057@pusan.ac.kr")) {
                session.setAttribute("admin", loginMember.getId());
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
    }

    @GetMapping("/members/changepwd")
    public String changePwd(Model model) {
        model.addAttribute("changepwdForm", new changepwdForm());

        return "members/changepassword";
    }

    @PostMapping("/members/changepwd")
    public String changePassword(@Valid changepwdForm changepwdform, BindingResult result, HttpSession session, Model model) {
        try {
            memberService.CheckAndUpdate(session, changepwdform);

            if (result.hasErrors()) {
                model.addAttribute("data", new Messsage("모든 항목을 채워주십시오.", "/members/changepwd"));

                return "message";
            }

        } catch (IllegalStateException exception) {
            model.addAttribute("data", new Messsage(exception.getMessage(), "/members/changepwd"));

            return "message";
        }

        model.addAttribute("data", new Messsage("비밀번호 변경을 완료하였습니다. 다시 로그인 해주십시오", "/members/login"));

        session.invalidate();

        return "message";
    }

    @GetMapping("/members/findpwd")
    public String findPassword(Model model) {
        model.addAttribute("findPwdForm", new findPwdForm());

        log.info("진입");

        return "members/findbyemail";
    }

    @PostMapping("/members/findpwd")
    public String change_Pwd_by_Email(@Valid findPwdForm pwdForm, Model model) {
        try {
            log.info("이메일 생성");
            EmailForm emailForm = emailService.createMailAndChangePwd(pwdForm);

            log.info("이메일 전송");
            emailService.sendEmail(emailForm);
        } catch (IllegalStateException exception) {
            model.addAttribute("data", new Messsage(exception.getMessage(), "/members/findpwd"));

            return "message";
        }
        model.addAttribute("data", new Messsage("로그인 페이지로 이동합니다", "/members/login"));

        return "message";
    }

    @GetMapping("/members/uploadlist")
    public String uploadList(Model model, HttpSession session) {

        Member member = memberService.getPresentMember((String)session.getAttribute("loginId"));

        List<Item> uploadlist = itemService.findUploadList(member);
        log.info("업로드 리스트 : " + uploadlist);
        model.addAttribute("uploadlist", uploadlist);
        model.addAttribute("newLineChar", '\n');

        return "members/uploadlist";
    }

    @GetMapping("/members/mypage")
    public String mypages(Model model, HttpSession session){
        String loginId = (String) session.getAttribute("loginId");
        Member loginMember = memberService.getPresentMember(loginId);

        List<Item> myList = itemService.MyItemList(loginId);
        long totalLikes = 0;
        long totalViews = 0;
        for (int i = 0; i < myList.size(); i++) {
            totalLikes += itemService.findItemById(myList.get(i).getId()).getLikes();
            totalViews += itemService.findItemById(myList.get(i).getId()).getViews();
        }

        model.addAttribute("isEqual", true);
        model.addAttribute("loginMember",loginMember);
        model.addAttribute("myList",myList);
        model.addAttribute("totalLikes", totalLikes);
        model.addAttribute("totalViews", totalViews);
        model.addAttribute("totalDesigns", myList.size());
        model.addAttribute("newLineChar",'\n');

        return "mypage";
    }

    @GetMapping("/members/{memberId}/mypage")
    public String portfolio(@PathVariable("memberId") String memberId, Model model, HttpSession session){
        Member member = memberService.getPresentMember(memberId);

        List<Item> itemList = itemService.MyItemList(memberId);
        long totalLikes = 0;
        long totalViews = 0;
        for (Item item : itemList) {
            totalLikes += item.getLikes();
            totalViews += item.getViews();
        }
        String loginId = (String) session.getAttribute("loginId");
        boolean isEqual = loginId.equals(memberId);
        model.addAttribute("isEqual", isEqual);
        model.addAttribute("loginMember", member);
        model.addAttribute("myList", itemList);
        model.addAttribute("totalLikes", totalLikes);
        model.addAttribute("totalViews", totalViews);
        model.addAttribute("totalDesigns",itemList.size());
        model.addAttribute("newLineChar",'\n');

        return "mypage";
    }
}
