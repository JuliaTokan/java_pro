package zip.demo.zip;

import lombok.Data;
import lombok.NoArgsConstructor;
import zip.demo.user.CustomUser;

import javax.persistence.*;

@Entity
@Data @NoArgsConstructor
public class ZipArchive {
    @Id
    @GeneratedValue
    private Long id;

    private String pathZipFile;

    @ManyToOne(fetch = FetchType.LAZY)
    private CustomUser user;

    /*@Lob
    @Column(columnDefinition = "BLOB")*/
    private byte[] zipFile;

    public ZipArchive(String pathZipFile, CustomUser user, byte[] zipFile) {
        this.pathZipFile = pathZipFile;
        this.user = user;
        this.zipFile = zipFile;
    }
}
