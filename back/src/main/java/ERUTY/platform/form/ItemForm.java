package ERUTY.platform.form;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter @Setter
public class ItemForm{
    @NotEmpty(message = "디자인 이름을 입력해주세요.")
    private String designName;
    @NotEmpty(message = "창작자를 입력해주세요.")
    private String creator;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "창작연월일을 입력해주세요.")
    private LocalDate createdDate;
    @NotEmpty(message = "창작 내용을 입력해주세요.")
    private String description;
    @NotNull(message = "가격를 입력해주세요.")
    private long price;
    @NotNull
    private boolean isOrigin;
    @NotNull
    private boolean canCommercialUse;
    @NotNull
    private boolean canModification;
}
