package test_annotation_reflection;

import java.lang.annotation.*;

@Inherited
@Target(value=ElementType.METHOD)
@Retention(value= RetentionPolicy.RUNTIME)

public @interface Saver{
}
