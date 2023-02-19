package ERUTY.platform.service;

import ERUTY.platform.domain.Item;
import ERUTY.platform.domain.Member;
import ERUTY.platform.form.MemberLoginForm;
import ERUTY.platform.form.changepwdForm;
import ERUTY.platform.repository.ItemRepository;
import ERUTY.platform.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final PasswordEncoder pwEncoder;

    public void saveMember(Member member) {
        validateDuplicateMember(member);

        setEncodePwd(member);

        memberRepository.save(member);
    }

    public void validateDuplicateMember(Member member) {
        List<Member> findMember = memberRepository.findMembersByEmail(member.getEmail());

        if(!findMember.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public void validateConfirmPassword(String pwd, String confirmpwd) {
        if(!(pwd.equals(confirmpwd))) {
            throw new IllegalStateException("비밀번호를 다시 확인해 주십시오.");
        }
    }

    public void setEncodePwd(Member member) {
        String encodePw = pwEncoder.encode(member.getPassword());
        log.info("비밀번호 인코딩 : " + encodePw);

        member.setPassword(encodePw);
    }

    public void CheckAndUpdate(changepwdForm changepwdform){
        String email = changepwdform.getEmail();
        Member member = memberRepository.findMemberByEmail(email);

        if(member == null){
            throw new IllegalStateException("존재하지 않는 이메일입니다");
        }
        String newpwd = changepwdform.getPassword();
        String newconfirm = changepwdform.getConfirmPassword();

        validateConfirmPassword(newpwd, newconfirm);
        member.setPassword(newpwd);

        setEncodePwd(member);

        memberRepository.save(member);
    }

    public Member findLoginMember(MemberLoginForm memberLoginForm) {
        validateExistEmail(memberLoginForm);

        Member findMember = memberRepository.findMemberByEmail(memberLoginForm.getEmail());

        validateLoginPassword(memberLoginForm.getPassword(), findMember.getPassword());

        return findMember;
    }

    private void validateExistEmail(MemberLoginForm memberLoginForm) {
        List<Member> findMembers = memberRepository.findMembersByEmail(memberLoginForm.getEmail());

        if(findMembers.isEmpty()) {
            throw new IllegalStateException("이메일을 다시 확인해주십시오.");
        }
    }

    public void validateLoginPassword(String rawPw, String encodePw) {
        if(!(pwEncoder.matches(rawPw, encodePw))) {
            throw new IllegalStateException("비밀번호를 다시 확인해 주십시오.");
        }
    }

    public List<Member> getMarketingMember() {
        return memberRepository.findAllByOrderByMarketingOkDesc();
    }

    public void uploadListUpdate(String itemId, String memberId) {

        Member member = memberRepository.findMemberById(memberId);

        log.info("업로드 리스트 추가");

        member.getUploadList().add(itemId);
        log.info("업로드된 아이템 : " + member.getUploadList());

        memberRepository.save(member);
    }

    public Member getPresentMember(String memberId) {
        return memberRepository.findMemberById(memberId);
    }

    /*public long calcMyLikes(String memberId) {
        //List<Item> myItems = itemRepository.findItemsByLoginId(memberId);
        Member member = memberRepository.findMemberById(memberId);
        List<String> uploadlist = member.getUploadList();
        long totalLikes = 0;
        for(int i = 0; i < uploadlist.size(); i++) {
            long likes = itemRepository.findItemById(uploadlist.get(i)).getLikes();
            totalLikes += likes;
        }
        return totalLikes;
    }

    public long calcMyViews(String memberId) {
        Member member = memberRepository.findMemberById(memberId);
        List<String> myItems = member.getUploadList();
        //List<String> uploadlist = member.getUploadList();
        long totalViews = 0;
        for(int i = 0; i < myItems.size(); i++) {
            long views = itemRepository.findItemById(myItems.get(i)).getViews();
            totalViews += views;
        }
        return totalViews;
    }*/

    public int calcMyDesigns(String memberId) {
        Member member = memberRepository.findMemberById(memberId);
        List<Item> myList = itemRepository.findItemsByMemberId(memberId);
        int listSize = myList.size();
        return listSize;
    }

    public List<Member> getTotalPNumber() {
        List<Member> memberList = memberRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        return memberList;
    }
}
