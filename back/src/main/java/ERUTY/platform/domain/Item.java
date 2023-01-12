package ERUTY.platform.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Item {

    private long likes; // 좋아요
    private long views; // 조회수
    
    private long price; // 가격
    private long size;  // 용량

    private String designName; // 디자인 명칭
    private String creator; // 창작자
    private LocalDate createdDate; // 창작연월일
    private String description; // 작품 설명
    private String tool; // 제작 툴

    @Builder
    public Item(String designName, String creator, LocalDate createdDate, String description, String tool, long price) {
        this.designName = designName;
        this.creator = creator;
        this.createdDate = createdDate;
        this.description = description;
        this.tool = tool;
        this.price = price;
    }
}