package test_annotation_reflection;

public class MyAnnotationTest {

    @MyAnnotation(a = 2, b = 5)
    public void test(int a, int b) {
        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }
}
