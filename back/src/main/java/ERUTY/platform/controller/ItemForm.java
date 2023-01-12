package ERUTY.platform.controller;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter @Setter
public class ItemForm{
    @NotEmpty(message = "디자인 이름을 입력해주세요.")
    private String designName;
    @NotEmpty(message = "창작자를 입력해주세요.")
    private String creator;
    @NotEmpty(message = "창작연월일을 입력해주세요.")
    private LocalDate createdDate;
    @NotEmpty(message = "창작 내용을 입력해주세요.")
    private String description;
    @NotEmpty(message = "제작 툴을 입력해주세요.")
    private String tool;
    @NotEmpty(message = "가격를 입력해주세요.")
    private long price;
}