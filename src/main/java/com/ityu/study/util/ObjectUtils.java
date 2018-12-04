package com.ityu.study.util;

import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author lihe
 */
@UtilityClass
public class ObjectUtils {


    public static <T> void whenNonNull(T t, Consumer<? super T> action) {
        if (Objects.nonNull(t)) {
            action.accept(t);
        }

    }


}
