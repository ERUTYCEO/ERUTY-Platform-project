package ERUTY.platform.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class ItemForm1 {
    @NotEmpty
    private String modelPath;

}
