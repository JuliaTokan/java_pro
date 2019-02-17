package test_annotation_reflection;

import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) {
        try {
            final Class<?> cls = MyAnnotationTest.class;
            MyAnnotationTest myAnnotationTest = new MyAnnotationTest();

            Method method = cls.getMethod("test", int.class, int.class);
            MyAnnotation myAnnotation = method.getAnnotation(MyAnnotation.class);
            method.invoke(myAnnotationTest, myAnnotation.a(), myAnnotation.b());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
