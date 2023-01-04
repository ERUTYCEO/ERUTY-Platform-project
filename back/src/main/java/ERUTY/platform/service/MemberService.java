package ERUTY.platform.service;

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
        log.info("member name : {}, email : {}, pwd : {}, cfpwd : {}", member.getName(), member.getEmail(), member.getPassword(), member.getConfirmpassword());
        validateDuplicateMember(member);
        validateConfirmPassword(member);
        memberRepository.save(member);
    }

    public void validateDuplicateMember(Member member) {
        List<Member> findMember = memberRepository.findMembersByEmail(member.getEmail());

        if(!findMember.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    private void validateConfirmPassword(Member member) {
        String pwd = member.getPassword();
        String confirmpwd = member.getConfirmpassword();

        if(!(pwd.equals(confirmpwd))) {
            throw new IllegalStateException("비밀번호를 다시 확인해 주십시오");
        }
    }

    public String selectMember(Member member) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            if(memberRepository.findMemberByEmail(member.getEmail()) == null) {
                return String.format("member email does not exist.");
            } else {
                return objectMapper.writeValueAsString(memberRepository.findMemberByEmail(member.getEmail()));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
}
