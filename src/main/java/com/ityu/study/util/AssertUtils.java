package com.ityu.study.util;


import com.ityu.study.exception.CommonException;
import com.ityu.study.status.GlobalStatusEnum;
import lombok.experimental.UtilityClass;
import lombok.var;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 验证工具
 * <p>
 * <p>
 * 验证逻辑-----> if false then throws
 * <p>
 * 如有逻辑不符合，尽快修改语意
 *
 * @author lihe
 */
@UtilityClass
@SuppressWarnings({"RedundantThrows", "unused"})
public class AssertUtils {


    public static void whenIsNull(Object obj, String message) throws CommonException {
        if (Objects.isNull(obj)) {
            throw new CommonException(message);
        }
    }

    public static void whenIsNull(Object obj, R.Enum em) throws CommonException {
        if (Objects.isNull(obj)) {
            throwMessage(em);
        }
    }


    public static <X extends CommonException> void whenIsNull(Object obj, Supplier<? extends X> es) throws X {
        if (Objects.isNull(obj)) {
            throw es.get();
        }
    }


    public static <X extends CommonException> void afterNow(LocalDateTime time, Supplier<? extends X> elseThrows) throws X {
        var rs = time.isAfter(LocalDateTime.now());
        baseCheck(rs, elseThrows);
    }

    public static <X extends CommonException> void beforeNow(LocalDateTime time, Supplier<? extends X> elseThrows) throws X {
        var rs = time.isBefore(LocalDateTime.now());
        baseCheck(rs, elseThrows);
    }


    private static void throwMessage(R.Enum em) {
        throw new CommonException(em);
    }

    public static void allIsNull(R.Enum em, Object... objs) throws CommonException {
        for (Object obj : objs) {
            if (Objects.nonNull(obj)) {
                return;
            }
        }
        throwMessage(em);
    }


    public static void notNormal(int statusCode, R.Enum em) throws CommonException {
        if (statusCode != GlobalStatusEnum.NORMAL.getCode()) {
            throwMessage(em);
        }
    }


    public static void notEq(Object obj, Object obj2, R.Enum em) throws CommonException {
        if (!Objects.equals(obj, obj2)) {
            throwMessage(em);
        }
    }

    public static <X extends Throwable> void notEq(Object obj, Object obj2, Supplier<? extends X> es) throws X {
        if (!Objects.equals(obj, obj2)) {
            throw es.get();
        }
    }

    public static <X extends Throwable> void eq(Object obj, Object obj2, Supplier<? extends X> es) throws X {
        if (Objects.equals(obj, obj2)) {
            throw es.get();
        }
    }


    public static <X extends CommonException> void notNormal(int statusCode, Supplier<? extends X> es) throws X {
        if (statusCode != GlobalStatusEnum.NORMAL.getCode()) {
            throw es.get();
        }
    }


    public static <X extends CommonException> void isFalse(boolean bol, Supplier<? extends X> es) throws X {
        if (!bol) {
            throw es.get();
        }
    }

    public static <X extends CommonException> void isTrue(boolean bol, Supplier<? extends X> es) throws X {
        if (!bol) {
            throw es.get();
        }
    }

    public static <X extends CommonException> void whenNotNull(Object obj, Supplier<? extends X> es) throws X {
        if (Objects.nonNull(obj)) {
            throw es.get();
        }
    }


    private static <X extends CommonException> void baseCheck(boolean bol, Supplier<? extends X> elseThrows) throws X {
        if (!bol) {
            throw elseThrows.get();
        }
    }


    /**
     * 判断是否已过目标时间
     *
     * @param time 目标时间
     * @param em   异常提醒信息
     */
    public static void overdue(long time, R.Enum em) {
        if (System.currentTimeMillis() > time) {
            throwMessage(em);
        }
    }

    /**
     * 判断是否已过目标时间
     *
     * @param time 目标时间
     * @param es   异常提醒信息
     */
    public static <X extends CommonException> void overdue(long time, Supplier<? extends X> es) throws X {
        if (System.currentTimeMillis() > time) {
            throw es.get();
        }
    }

    /**
     * 判断传入的参数均为null
     *
     * @param es  异常
     * @param obj 对象列表
     * @param <X> 异常对象的泛型
     * @throws X 当符合条件时间抛出该异常
     */
    public static <X extends CommonException> void allIsNull(Supplier<? extends X> es, Object... obj) throws X {
        for (Object o : obj) {
            if (Objects.nonNull(o)) {
                return;
            }
        }
        throw es.get();
    }


    public static void whenNotNull(Object obj, R.Enum em) {
        if (Objects.nonNull(obj)) {
            throwMessage(em);
        }
    }

    public static void isFalse(boolean bol, R.Enum em) {
        if (!bol) {
            throwMessage(em);
        }
    }


}
