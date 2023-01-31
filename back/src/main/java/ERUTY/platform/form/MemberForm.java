package ERUTY.platform.form;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    private String name;
    private String email;
    private String password;
    private String confirmpassword;
    private boolean marketingOk;
}
