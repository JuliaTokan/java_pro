package test_annotation_reflection;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;

public class Main {

    public static void main(String[] args) {
        TestSaveClass testSerialization = new TestSaveClass(3, "string1", 4.5, "string2");
        serialization(testSerialization, "/Users/yulia/Documents/test.txt");

        TestSaveClass testDeserialization = new TestSaveClass();
        deserialization(testDeserialization, "/Users/yulia/Documents/test.txt");
        System.out.println("param1 = " + testDeserialization.getParameter1());
        System.out.println("param2 = " + testDeserialization.getParameter2());
        System.out.println("param3 = " + testDeserialization.getParameter3());
        System.out.println("param4 = " + testDeserialization.getParameter4());
    }

    public static void serialization(Object object, String path) {
        try {
            FileOutputStream outputStream = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            Class<?> cls = object.getClass();
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Save.class)) {
                    field.setAccessible(true);
                    Object obj = field.get(object);
                    objectOutputStream.writeObject(obj);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void deserialization(Object object, String path) {
        try {
            FileInputStream inputStream = new FileInputStream(path);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            Class<?> cls = object.getClass();
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Save.class)) {
                    field.setAccessible(true);
                    field.set(object, objectInputStream.readObject());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
