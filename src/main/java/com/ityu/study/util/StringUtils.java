package com.ityu.study.util;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author lihe
 */
@UtilityClass
public class StringUtils {

    /**
     * 默认的多数据分隔符
     */
    private static final String DEFAULT_MULTIPLE_DATA_SPLIT = ",";


    public static Stream<String> spiltAsStream(String data) {
        return Arrays.stream(data.split(DEFAULT_MULTIPLE_DATA_SPLIT));

    }


    public static boolean notEmpty(String str) {


        if (Objects.isNull(str)) {
            return false;
        } else {
            return !str.isEmpty();
        }
    }

    public static boolean isEmpty(String string) {
        return !notEmpty(string);
    }


}
