package ERUTY.platform.controller;

import lombok.*;
import javax.validation.constraints.NotEmpty;


@Getter @Setter
public class changepwdForm {
    @NotEmpty(message = "Email을 입력해주세요.")
    private String email;
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;
    @NotEmpty(message = "비밀번호를 확인해주세요.")
    private String confirmpassword;
}