package ERUTY.platform.form;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;
    @NotEmpty(message = "E-mail을 입력해주세요.")
    private String email;
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;
    @NotEmpty(message = "비밀번호를 확인해주세요.")
    private String confirmpassword;
    private boolean marketingOk;
}
