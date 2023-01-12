package ERUTY.platform.service;

import ERUTY.platform.controller.MemberForm;
import ERUTY.platform.controller.changepwdForm;
import ERUTY.platform.controller.MemberLoginForm;
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
            throw new IllegalStateException("비밀번호를 다시 확인해 주십시오.");
        }
    }
    public void CheckAndUpdate(changepwdForm changepwdform){
        String email = changepwdform.getEmail();
        Member member = memberRepository.findMemberByEmail(email);

        if(member == null){
            throw new IllegalStateException("존재하지 않는 이메일입니다");
        }
        String newpwd = changepwdform.getPassword();
        String newconfirm = changepwdform.getConfirmPassword();

        if(!(newpwd.equals(newconfirm))){
            throw new IllegalStateException("비밀번호를 다시 확인해 주십시오.");
        }

        Member newMember = new Member(member.getName(), member.getEmail(), newpwd);

        memberRepository.save(newMember);
        memberRepository.delete(member);

    }
    public Member findLoginMember(MemberLoginForm memberLoginForm) {
        validateExistEmail(memberLoginForm);

        Member findMember = memberRepository.findMemberByEmail(memberLoginForm.getEmail());
        validateCorrectPassword(memberLoginForm, findMember);

        return findMember;
    }

    private void validateExistEmail(MemberLoginForm memberLoginForm) {
        List<Member> findMembers = memberRepository.findMembersByEmail(memberLoginForm.getEmail());

        if(findMembers.isEmpty()) {
            throw new IllegalStateException("이메일을 다시 확인해주십시오.");
        }
    }

    private void validateCorrectPassword(MemberLoginForm memberLoginForm, Member findMember) {
        if(!(memberLoginForm.getPassword().equals(findMember.getPassword()))) {
            throw new IllegalStateException("비밀번호를 다시 확인해주십시오");
        }
    }
}
