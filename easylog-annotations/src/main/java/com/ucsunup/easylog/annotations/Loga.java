package com.ucsunup.easylog.annotations;

import com.ucsunup.easylog.Level;

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
public @interface Loga {

    /**
     * Define the log type
     *
     * @return log type
     */
    Level level() default Level.VERBOSE;

    /**
     * Set if print out parameters of method
     *
     * @return true, print.
     */
    boolean printArgs() default true;
}
