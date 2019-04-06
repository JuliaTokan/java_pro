package zip.demo.zip;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipFile;

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

    /*public static byte[] toZip(MultipartFile file, String zipPath) throws Exception {
        byte[] zipFile = new byte[0];
        if(!file.isEmpty()) {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipPath));
            zipFile = doZip(file, out);
            out.close();
        }
        return zipFile;
    }

    private static byte[] doZip(MultipartFile file, ZipOutputStream out) throws IOException {
        out.putNextEntry(new ZipEntry(file.getOriginalFilename()));
        return ((ByteArrayOutputStream)out).toByteArray();
    }

    private static void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        in.close();
    }

    private static void saveZip(byte[] file, String zipPath) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipPath));
        ByteArrayInputStream in = new ByteArrayInputStream(file);
        write(in, out);
    }*/
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
        /*ZipInputStream zipInStream = new ZipInputStream(new ByteArrayInputStream(file));
        ZipEntry entry = null;
        System.out.println("We are in save zip!");
        if ((entry = zipInStream.getNextEntry()) != null) {
            System.out.println("Entry not empty!");
            String entryName = entry.getName();
            FileOutputStream out = new FileOutputStream(entryName);
            byte[] buff = new byte[4096];
            int bytesread = 0;
            while ((bytesread = zipInStream.read(buff)) != -1) {
                out.write(buff, 0, bytesread);
            }
            out.close();
            zipInStream.closeEntry();
            ;
            zipInStream.close();
        }*/
        try (FileOutputStream fos = new FileOutputStream(zipPath)) {
            fos.write(file);
        }
    }
}
