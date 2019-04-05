package zip.demo.zip;

import lombok.Data;
import lombok.NoArgsConstructor;
import zip.demo.user.CustomUser;

import javax.persistence.*;
import java.util.zip.ZipEntry;

@Entity
@Data @NoArgsConstructor
public class ZipArchive {
    @Id
    @GeneratedValue
    private Long id;

    private String pathZipFile;

    private String login;

    public ZipArchive(String pathZipFile, String login) {
        this.pathZipFile = pathZipFile;
        this.login = login;
    }
}
