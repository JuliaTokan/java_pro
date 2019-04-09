package zip.demo.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data @NoArgsConstructor
public class CustomUser {
    @Id
    @GeneratedValue
    private Long id;

    private String login;
    private String password;

    private String role;

    public CustomUser(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
