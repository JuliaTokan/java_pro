package zip.demo.zip;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.springframework.web.multipart.MultipartFile;

public class ZipUtil {

    public static String toZip(MultipartFile file) throws Exception {
        String path = file.getOriginalFilename();
        path = path.substring(0,path.indexOf('.'));
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(path+".zip"));
        doZip(file, out);
        out.close();
        return path+".zip";
    }

    private static void doZip(MultipartFile file, ZipOutputStream out) throws IOException {
        out.putNextEntry(new ZipEntry(file.getOriginalFilename()));
        write(file.getInputStream(), out);
    }

    private static void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        in.close();
    }
}
