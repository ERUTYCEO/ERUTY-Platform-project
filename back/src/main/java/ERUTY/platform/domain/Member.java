package ERUTY.platform.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "member")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private boolean marketingOk;
    private List<Item> uploadList;

    public Member(String name, String email, String password, boolean marketingOk) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.marketingOk = marketingOk;
    }
}