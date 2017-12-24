package com.ucsunup.easylog.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ucsunup on 2017/12/2.
 */

@Target({
        ElementType.TYPE,
        ElementType.METHOD,
        ElementType.CONSTRUCTOR,
        ElementType.FIELD,
        ElementType.LOCAL_VARIABLE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface Logit {
    enum Type {Debug, Release}

    /**
     * Define the log type
     *
     * @return log type
     */
    Type type() default Type.Debug;

    /**
     * Append string to log
     *
     * @return
     */
    String append() default "";
}
