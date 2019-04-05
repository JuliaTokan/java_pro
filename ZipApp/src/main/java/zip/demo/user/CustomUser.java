package zip.demo.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import zip.demo.user.UserRole;

import javax.persistence.*;

@Entity
@Data @NoArgsConstructor
public class CustomUser {
    @Id
    @GeneratedValue
    private Long id;

    private String login;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public CustomUser(String login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
