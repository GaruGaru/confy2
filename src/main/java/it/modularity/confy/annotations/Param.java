package it.modularity.confy.annotations;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface Param {

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Float {
        java.lang.String key();
        float defaultValue();
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Integer {
        java.lang.String key();
        int defaultValue();
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface String {
        java.lang.String key();
        java.lang.String defaultValue() default "";
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Boolean {
        java.lang.String key();
        boolean defaultValue();
    }

}
