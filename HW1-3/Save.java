package test_annotation_reflection;

import java.lang.annotation.*;

@Inherited
@Target(value=ElementType.FIELD)
@Retention(value= RetentionPolicy.RUNTIME)

public @interface Save{
}
