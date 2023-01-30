package ERUTY.platform.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class findItemForm {
    @NotEmpty(message = "디자인 이름 입력.")
    private String searchKeyword;
}