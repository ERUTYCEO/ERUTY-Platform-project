package ERUTY.platform.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "item")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id
    private String id;
    private String memberId;
    private long likes;
    private long views;
    private long price;
    private long numBuy;

    private String designName;
    private String creator;
    private Date createdDate;
    private String description;
    private boolean isOrigin;
    private boolean canCommercialUse;
    private boolean canModification;

    private String modelPath;
    private String imagePath;

    private boolean liked;
    private List<String> likedList = new ArrayList<>();

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

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
