package ERUTY.platform.controller;

import ERUTY.platform.service.MemberService;
import org.springframework.beans.factory.annotation.*;
import ERUTY.platform.domain.*;
import ERUTY.platform.repository.MemberRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import javax.validation.constraints.NotEmpty;
import java.util.List;


@Getter @Setter
public class changepwdForm {

    public void changePassword(MemberRepository memberRepository, MemberForm memberForm, Member member){
        //List<Member> findmember = memberRepository.findMembersByEmail(email);
        @NotEmpty(message = "Email을 입력해주세요")
        String email;
        Member findmember = memberRepository.findMemberByEmail(email);
        if(findmember != null){
            @NotEmpty(message = "원하시는 비밀번호를 입력해주세요")
            String newpwd;
            @NotEmpty(message = "비밀번호를 재입력해주세요")
            String newconfirm;

            if(!(newpwd.equals(newconfirm))){
                throw new IllegalStateException("비밀번호와 확인 비밀번호가 일치하지 않습니다.");
            }
            else if(newpwd.equals(newconfirm)){
                //findmember.setPassword(newpwd);
                findmember.setPassword(newpwd);
                memberRepository.save(findmember);
                System.out.println("변경되었습니다");
            }
        }
        else if(findmember == null){
            throw new IllegalStateException("이메일이 존재하지 않습니다.");
        }


    }
}