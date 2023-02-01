package ERUTY.platform.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class Messsage {

    private String message;
    private String href;

    public Messsage(String message, String href) {
        this.message = message;
        this.href = href;
    }
}
