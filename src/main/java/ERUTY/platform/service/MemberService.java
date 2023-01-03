package ERUTY.platform.service;

import ERUTY.platform.domain.Member;
import ERUTY.platform.repository.MemberRepository;
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
        validatePasswordCheck(member);
        memberRepository.save(member);
    }

    public void validateDuplicateMember(Member member) {
        List<Member> findMember = memberRepository.findMembersByEmail(member.getEmail());

        if(!findMember.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    private void validatePasswordCheck(Member member) {
        if(!(member.getPassword() == member.getConfirmpassword())) {
            throw new IllegalStateException("비밀번호 확인을 다시 해주십시오.");
        }
    }
}
