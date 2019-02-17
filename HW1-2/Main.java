package test_annotation_reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) {
        SaveTest saveTest = new SaveTest();
        //saveTest.save("/Users/yulia/Documents/projects_java/Java-Web/HW-2-Annotation-Reflection/test.txt");
        try {
            final Class<?> cls = SaveTest.class;
            if (cls.isAnnotationPresent(SaveTo.class)) {
                Annotation annotation = cls.getAnnotation(SaveTo.class);
                Method[] methods = cls.getMethods();
                for(Method method: methods){
                    if(method.isAnnotationPresent(Saver.class)){
                        method.invoke(saveTest, ((SaveTo) annotation).path());
                    }
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
