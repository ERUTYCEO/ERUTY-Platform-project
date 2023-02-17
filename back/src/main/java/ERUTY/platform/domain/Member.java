package ERUTY.platform.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
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
    private String phoneNumber;
    private List<String> uploadList = new ArrayList<>();

    public Member(String name, String email, String password, String phoneNumber, boolean marketingOk) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.marketingOk = marketingOk;
    }
}