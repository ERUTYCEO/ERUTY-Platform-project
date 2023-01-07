package ERUTY.platform.controller;

import ERUTY.platform.service.*;
import ERUTY.platform.domain.*;
import ERUTY.platform.repository.MemberRepository;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter @Setter
public class changepwdForm {
    Member member;
    MemberForm memberForm;
    @NotEmpty(message = "Email을 입력해주세요.")
    String email;
    if(memberForm.email.euqals(email)){
        @NotEmpty(message = "현재 비밀번호를 입력해주세요.")
        String password;
        if (memberForm.password.equals(password)) {
            @NotEmpty(message = "원하시는 비밀번호를 입력해주세요.")
            private String NewPWD;

            @NotEmpty(message = "확인을 위해 다시 입력해주세요.")
            private String NewConfirm;
            public void changePassword (Member member, MemberForm memberForm){
                System.out.println("원하시는 비밀번호를 입력해 주십시오.");
                //String password = memberForm.getPassword();
                //String confirmPassword = memberForm.getConfirmpassword();
                if (!(NewPWD.equals(NewConfirm))) {
                    throw new IllegalStateException("비밀번호를 다시 입력해 주십시오.");
                } else if (NewPWD.equals(NewConfirm)) {
                    memberForm.setPassword(password);
                    System.out.println("변경되었습니다.");
                }

            }

        }
    }
    else if(!(memberForm.email.equals(email)))
    {
        System.out.println("이메일이 일치하지 않습니다.");
    }

    /*@NotEmpty(message = "현재 비밀번호를 입력해주세요.")
    String password;

    @NotEmpty(message = "원하시는 비밀번호를 입력해주세요.")
    private String NewPWD;

    @NotEmpty(message = "확인을 위해 다시 입력해주세요.")
    private String NewConfirm;
    public void changePassword(Member member, MemberForm memberForm) {
        System.out.println("원하시는 비밀번호를 입력해 주십시오.");
        //String password = memberForm.getPassword();
        //String confirmPassword = memberForm.getConfirmpassword();
        if(!(NewPWD.equals(NewConfirm))) {
            throw new IllegalStateException("비밀번호를 다시 입력해 주십시오.");
        } else if (NewPWD.equals(NewConfirm)) {
            memberForm.setPassword(password);
            System.out.println("변경되었습니다.");
        }

    }*/

}
