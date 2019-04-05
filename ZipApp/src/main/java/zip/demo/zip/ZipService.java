package zip.demo.zip;

import zip.demo.user.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ZipService {
    @Autowired
    private ZipRepository zipRepository;

    @Transactional
    public void addZip(String pathZip,String parhOriginFile, String user) {
        ZipArchive zipArchive = new ZipArchive(pathZip, parhOriginFile, user);
        zipRepository.save(zipArchive);
    }

    @Transactional(readOnly = true)
    public List<ZipArchive> findZipByLogin(String login) {
        return zipRepository.findZipByLogin(login);
    }

    @Transactional(readOnly = true)
    public String findFileByZip(String pathZip) {
        return zipRepository.findOriginFilePath(pathZip);
    };

}
