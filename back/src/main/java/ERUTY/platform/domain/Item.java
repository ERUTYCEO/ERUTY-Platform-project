package ERUTY.platform.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Date;
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
    private Date createdDate; // 창작연월일
    private String description; // 작품 설명
    private boolean isOrigin;
    private boolean canCommercialUse;
    private boolean canModification;

    @Builder
    public Item(String designName, String creator, Date createdDate,
                String description, long price, boolean isOrigin,
                boolean canModification, boolean canCommercialUse) {
        this.designName = designName;
        this.creator = creator;
        this.createdDate = createdDate;
        this.description = description;
        this.price = price;
        this.isOrigin = isOrigin;
        this.canModification = canModification;
        this.canCommercialUse = canCommercialUse;
    }
}
