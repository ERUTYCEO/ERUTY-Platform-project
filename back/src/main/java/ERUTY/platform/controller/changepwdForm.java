package ERUTY.platform.controller;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class changepwdForm {
    @NotEmpty(message = "email을 입력해주세요.")
    private String email;

    @NotEmpty(message = "새 비밀번호를 입력해주세요.")
    private String password;

    @NotEmpty(message = "확인용 비밀번호를 입력해주세요")
    private String confirmPassword;
}
