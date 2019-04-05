package zip.demo.zip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface ZipRepository extends JpaRepository<ZipArchive, Long> {
    @Query("SELECT z FROM ZipArchive z where z.login = :login")
    List<ZipArchive> findZipByLogin(@Param("login") String login);
}
