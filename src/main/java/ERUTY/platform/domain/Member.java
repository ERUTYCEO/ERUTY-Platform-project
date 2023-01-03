package ERUTY.platform.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String confirmpassword;

    @Builder
    public Member(String name, String email, String password, String confirmpassword) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmpassword = confirmpassword;
    }
}
