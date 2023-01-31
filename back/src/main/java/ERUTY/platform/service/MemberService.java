package ERUTY.platform.service;

import ERUTY.platform.Exception.MemberException;
import ERUTY.platform.Exception.MemberExceptionType;
import ERUTY.platform.form.EmailForm;
import ERUTY.platform.form.changepwdForm;
import ERUTY.platform.form.MemberLoginForm;
import ERUTY.platform.form.findPwdForm;
import ERUTY.platform.domain.Member;
import ERUTY.platform.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void saveMember(Member member) {
        validateDuplicateMember(member);

        memberRepository.save(member);
    }

    public void validateDuplicateMember(Member member) {
        List<Member> findMember = memberRepository.findMembersByEmail(member.getEmail());

        if(!findMember.isEmpty()) {
           throw new MemberException(MemberExceptionType.ALREADY_EXIST_EMAIL);
        }
    }

    public void validateConfirmPassword(String pwd, String confirmpwd) {
        if(!(pwd.equals(confirmpwd))) {
            throw new MemberException(MemberExceptionType.WRONG_PASSWORD);
        }
    }

    public void CheckAndUpdate(changepwdForm changepwdform){
        String email = changepwdform.getEmail();
        Member member = memberRepository.findMemberByEmail(email);

        if(member == null){
            throw new MemberException(MemberExceptionType.NOT_FOUND_MEMBER);
        }
        String newpwd = changepwdform.getPassword();
        String newconfirm = changepwdform.getConfirmPassword();

        validateConfirmPassword(newpwd, newconfirm);

        member.setPassword(newpwd);
        memberRepository.save(member);
    }

    public Member findLoginMember(MemberLoginForm memberLoginForm) {
        validateExistEmail(memberLoginForm);

        Member findMember = memberRepository.findMemberByEmail(memberLoginForm.getEmail());
        validateConfirmPassword(memberLoginForm.getPassword(), findMember.getPassword());

        return findMember;
    }

    private void validateExistEmail(MemberLoginForm memberLoginForm) {
        List<Member> findMembers = memberRepository.findMembersByEmail(memberLoginForm.getEmail());

        if(findMembers.isEmpty()) {
            throw new MemberException(MemberExceptionType.NOT_FOUND_MEMBER);
        }
    }

    public EmailForm find_Email_by_Email_and_Name(findPwdForm pwdForm) {
        Member findMember = memberRepository.findMemberByEmail(pwdForm.getEmail());

        if(findMember == null) {
            throw new IllegalStateException("이메일을 다시 확인해주세요");
        }

        if(memberEmailCheck(findMember, pwdForm.getName()) == false) {
            throw new IllegalStateException("이름이 일치하지 않습니다.");
        }

        String email = findMember.getEmail();
        String name = findMember.getName();
        String newPwd = getTempPwd();

        EmailForm emailForm = new EmailForm();
        emailForm.setEmail(email);
        emailForm.setTitle(name + "님의 ERUTY 임시비밀번호 안내 이메일 입니다.");
        emailForm.setMessage("안녕하세요. ERUTY 임시비밀번호 안내 관련 이메일 입니다." + "[" + name + "]" +"님의 임시 비밀번호는 " + newPwd + " 입니다.");

        updatePwd(newPwd, findMember);

        return emailForm;
    }

    public boolean memberEmailCheck(Member member, String name) {
        if(member.getName().equals(name)) {
            return true;
        }
        else {
            return false;
        }
    }

    private void updatePwd(String newPwd, Member findMember) {
        findMember.setPassword(newPwd);
    }

    private String getTempPwd() {
        char[] charSet = new char[]  { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String newPwd = "";

        int index = 0;

        for(int i = 0; i < 13; i++) {
            index = (int) (charSet.length * Math.random());
            newPwd += charSet[index];
        }

        return newPwd;
    }

    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "arheon2469@gmail.com";

    public void sendEmail(EmailForm emailForm) {
        log.info("이메일 전송 완료");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailForm.getEmail());
        mailMessage.setFrom(FROM_ADDRESS);
        mailMessage.setSubject(emailForm.getTitle());
        mailMessage.setText(emailForm.getMessage());

        mailSender.send(mailMessage);
    }

    public List<Member> getMarketingMember() {
        return memberRepository.findAllByOrderByMarketingOkDesc();
    }

    public void uploadListUpdate(String itemId, String memberId) {

        Member member = memberRepository.findMemberById(memberId);

        member.getUploadList().add(itemId);
    }
}
