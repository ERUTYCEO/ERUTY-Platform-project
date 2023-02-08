package ERUTY.platform.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "item")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id
    private String id;
    private String memberId;
    private long likes; // 좋아요
    private long views; // 조회수

    private long price; // 가격

    private String designName; // 디자인 명칭
    private String creator; // 창작자
    private Date createdDate; // 창작연월일
    private String description; // 작품 설명
    private boolean isOrigin;
    private boolean canCommercialUse;
    private boolean canModification;

    private String modelPath;
    private String imagePath;

    private int liked;





    @Builder
    public Item(String designName, String creator, Date createdDate,
                String description, long price, boolean isOrigin,
                boolean canModification, boolean canCommercialUse,
                String modelPath, String imagePath) {
        this.designName = designName;
        this.creator = creator;
        this.createdDate = createdDate;
        this.description = description;
        this.price = price;
        this.isOrigin = isOrigin;
        this.canModification = canModification;
        this.canCommercialUse = canCommercialUse;
        this.modelPath = modelPath;
        this.imagePath = imagePath;
    }
    public void viewPlusOne(){
        this.views++;
    }

    public void setLikes(long liked) {
        this.likes = liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }
}
