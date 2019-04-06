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

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] zipFile;

    public ZipArchive(String pathZipFile, String login, byte[] file) {
        this.pathZipFile = pathZipFile;
        this.login = login;
        this.zipFile = file;
    }
}
