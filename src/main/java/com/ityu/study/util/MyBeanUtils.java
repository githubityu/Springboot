package com.ityu.study.util;

import com.ityu.study.function.CopyProcess;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lihe
 */
@UtilityClass
@Slf4j
public class MyBeanUtils {
    /**
     * 数据复制为一个列表
     *
     * @param source 原始数据
     * @param sp     目标数初始化方法
     * @param <T>    目标数据类型
     * @param <S>    原始数据类型
     * @return 拷贝后的列表
     */
    public static <T, S> List<T> lsCopy(List<S> source, Supplier<T> sp) {
        return lsCopy(source, sp, null);
    }

    /**
     * 数据复制为一个列表,并对复制后的对象执行操作
     *
     * @param source 原始数据
     * @param sp     目标数初始化方法
     * @param action 复制完一个对象后完成操作
     * @param <T>    目标数据类型
     * @param <S>    原始数据类型
     * @return 拷贝后的列表
     */
    public static <T, S> List<T> lsCopy(List<S> source, Supplier<T> sp, CopyProcess<S, T> action) {
        return lsCopy(source.stream(), sp, action);

    }

    public static <T, S> List<T> lsCopy(Stream<S> source, Supplier<T> sp) {
        return lsCopy(source, sp, null);
    }


    public static <T, S> List<T> lsCopy(Stream<S> source, Supplier<T> sp, CopyProcess<S, T> action) {
        return source.map(x -> {
            T target = copy(x, sp.get());
            if (Objects.nonNull(action)) {
                target = action.process(x, target);
            }
            return target;
        }).collect(Collectors.toList());
    }


    public static <S, T> T spCopy(S s, Supplier<T> supplier, String... ignoreProperties) {
        T t = supplier.get();
        BeanUtils.copyProperties(s, t, ignoreProperties);
        return t;
    }

    public static <S, T> T spCopy(S s, Supplier<T> supplier, CopyProcess<S, T> action) {
        T t = supplier.get();
        BeanUtils.copyProperties(s, t);
        if (Objects.nonNull(action)) {
            t = action.process(s, t);
        }
        return t;
    }


    public static <S, T> T spCopy(S s, Supplier<T> supplier) {
        return spCopy(s, supplier, (CopyProcess<S, T>) null);
    }

    public static <T> T copy(Object s, T target) {
        BeanUtils.copyProperties(s, target);
        return target;
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        return Arrays.stream(src.getPropertyDescriptors())
                .filter(x -> Objects.isNull(src.getPropertyValue(x.getName())))
                .map(FeatureDescriptor::getName)
                .toArray(String[]::new);

    }

    /**
     * 忽略null拷贝数据
     *
     * @param src    原对象
     * @param target 目标对象
     */
    public static void nonNullCopyPropertiesNonNull(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }


}
