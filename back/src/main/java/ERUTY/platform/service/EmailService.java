package ERUTY.platform.service;

import ERUTY.platform.domain.Member;
import ERUTY.platform.form.EmailForm;
import ERUTY.platform.form.findPwdForm;
import ERUTY.platform.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final MemberRepository memberRepository;
    private final JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "eruty7057@naver.com";

    public EmailForm createMailAndChangePwd(findPwdForm pwdForm) {
        Member member = memberRepository.findMemberByEmail(pwdForm.getEmail());

        if(member == null) {
            throw new IllegalStateException("이메일을 다시 확인해주세요");
        }
        if(memberEmailCheck(member, pwdForm.getName()) == false) {
            throw new IllegalStateException("이름이 일치하지 않습니다.");
        }

        String newPwd = getTempPwd();
        String memberName = pwdForm.getName();
        String memberEmail = pwdForm.getEmail();

        EmailForm emailForm = new EmailForm();
        emailForm.setEmail(memberEmail);
        emailForm.setTitle(memberName + "님의 ERUTY 임시비밀번호 안내 이메일 입니다.");
        emailForm.setMessage("안녕하세요. ERUTY 임시비밀번호 안내 관련 이메일 입니다." + "[" + memberName + "]" +"님의 임시 비밀번호는 " + newPwd + " 입니다.");

        updatePwd(newPwd, member);

        return emailForm;
    }

    private void updatePwd(String newPwd, Member member) {
        member.setPassword(newPwd);
        memberRepository.save(member);
    }

    private String getTempPwd() {
        char[] charSet = new char[]  {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                'U', 'V', 'W', 'X', 'Y', 'Z'
        };

        String newPwd = "";

        int index = 0;

        for(int i = 0; i < 13; i++) {
            index = (int) (charSet.length * Math.random());
            newPwd += charSet[index];
        }

        return newPwd;
    }

    private boolean memberEmailCheck(Member member, String name) {
        if(member.getName().equals(name)) {
            return true;
        }
        else {
            return false;
        }
    }


    public void sendEmail(EmailForm emailForm) {
        log.info("이메일 전송 완료");

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(emailForm.getEmail());
        mailMessage.setFrom(EmailService.FROM_ADDRESS);

        mailMessage.setSubject(emailForm.getTitle());
        mailMessage.setText(emailForm.getMessage());

        log.info("title : " + emailForm.getTitle());
        log.info("mailmessage : " + mailMessage);

        mailSender.send(mailMessage);
    }
}
