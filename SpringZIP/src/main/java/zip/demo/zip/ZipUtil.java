package zip.demo.zip;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipFile;

import org.springframework.web.multipart.MultipartFile;

public class ZipUtil {
    public static byte[] toZip(MultipartFile mfile) throws Exception {
        System.out.println("We are in to zip!");
        byte[] input = mfile.getBytes();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        ZipEntry entry = new ZipEntry(mfile.getOriginalFilename());
        entry.setSize(input.length);
        zos.putNextEntry(entry);
        zos.write(input);
        zos.closeEntry();
        zos.close();
        return baos.toByteArray();
    }

    public static void saveZip(byte[] file, String zipPath) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(zipPath)) {
            fos.write(file);
        }
    }
}
