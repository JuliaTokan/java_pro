package test_annotation_reflection;

import java.io.File;
import java.io.FileWriter;

@SaveTo(path="/Users/yulia/Documents/projects_java/Java-Web/HW-2-Annotation-Reflection/test.txt")
public class SaveTest {
    String text = "Saved!!!";

    @Saver
    public void save(String path){
        File file = new File(path);
        try(FileWriter writer = new FileWriter(file)){
            writer.write(text);
            writer.flush();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
