package ERUTY.platform.service;

import ERUTY.platform.controller.MemberForm;
import ERUTY.platform.domain.Member;
import ERUTY.platform.repository.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public void validateConfirmPassword(MemberForm memberForm) {
        String pwd = memberForm.getPassword();
        String confirmpwd = memberForm.getConfirmpassword();

        if(!(pwd.equals(confirmpwd))) {
            throw new IllegalStateException("비밀번호를 다시 확인해 주십시오");
        }
    }
}
