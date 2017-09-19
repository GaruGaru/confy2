package it.modularity.confy.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
public @interface Param {

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Float {
        java.lang.String key() default "";

        float defaultValue() default 0.0F;
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Integer {
        java.lang.String key() default "";

        int defaultValue() default 0;
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface String {
        java.lang.String key() default "";

        java.lang.String defaultValue() default "";
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Boolean {
        java.lang.String key() default "";

        boolean defaultValue() default false;
    }

}
