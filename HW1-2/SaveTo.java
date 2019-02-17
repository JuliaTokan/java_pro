package test_annotation_reflection;

import java.lang.annotation.*;

@Inherited
@Target(value=ElementType.TYPE)
@Retention(value= RetentionPolicy.RUNTIME)

public @interface SaveTo{
    String path() default "/Users/yulia/Documents/test.txt";
}
