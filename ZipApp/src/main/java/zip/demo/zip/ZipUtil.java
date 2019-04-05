package zip.demo.zip;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.springframework.web.multipart.MultipartFile;

public class ZipUtil {

    /*public static String toZip(MultipartFile file) throws Exception {
        String path = file.getOriginalFilename();
        path = path.substring(0,path.indexOf('.'));
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(path+".zip"));
        doZip(file, out);
        out.close();
        return path+".zip";
    }*/

    public static void toZip(String filePath, String zipPath) throws Exception {
        File file = new File(filePath);
        if(file.exists()) {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipPath));
            doZip(file, out);
            out.close();
        }
    }

    private static void doZip(File file, ZipOutputStream out) throws IOException {
        out.putNextEntry(new ZipEntry(file.getPath()));
        write(new FileInputStream(file), out);
    }

    private static void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        in.close();
    }
}
