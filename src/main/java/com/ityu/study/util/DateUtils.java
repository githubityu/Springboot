package com.ityu.study.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * 时间工具类
 *
 * @author lihe
 */
@Slf4j
public class DateUtils {
    private DateUtils() {
    }

    /**
     * 格式化时间
     *
     * @param patten 时间格式
     * @param time   时间对象
     * @return 格式化结果
     */
    public static String format(String patten, Date time) {
        return Objects.isNull(time) ? null : new SimpleDateFormat(patten).format(time);
    }


    /**
     * 格式化时间
     *
     * @param patten 时间格式
     * @return 格式化结果
     */
    public static String format(String patten) {

        return format(patten, new Date());
    }


    /**
     * 获取当前的时间戳对象
     *
     * @return 时间戳对象
     */
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 字符串时间转换成时间对象
     *
     * @param date   时间
     * @param patten 时间格式
     * @return 转换结果
     */
    public static Date parse(String date, String patten) {
        Date d = null;
        try {
            d = getTimeFormat(patten).parse(date);
        } catch (ParseException e) {
            log.error("date str convert to date object err,date:{},patten:{}", date, patten);
        }

        return Objects.isNull(d) ? new Date() : d;
    }


    private static SimpleDateFormat getTimeFormat(String patten) {
        return new SimpleDateFormat(patten);
    }


    /**
     * 获取当天剩余的秒钟数
     *
     * @return 当天剩余的秒数
     */
    public static Long getResidueSecondOfCurrentDay() {
        LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return ChronoUnit.SECONDS.between(LocalDateTime.now(), midnight);
    }

    /**
     * 判断是否在营业状态
     *
     * @param time 开始时间 HH:mm-HH:mm
     * @return 是否在营业中
     */
    public static boolean isOnOperation(String time) {
        boolean result = false;
        String start = time.substring(0, time.indexOf("-"));
        String end = time.substring(time.indexOf("-") + 1);
        LocalTime now = LocalTime.now();
        if (LocalTime.parse(start).compareTo(now) <= 0) {
            if (LocalTime.parse(end).compareTo(now) > 0) {
                result = true;
            }
        }

        return result;
    }


    public static String currentEnDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yy", Locale.ENGLISH));
    }

    public static String current(String patten) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(patten, Locale.ENGLISH));
    }


    public static String currentEnDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yy hh:mma", Locale.ENGLISH));
    }

    public static String currentEnTime() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mma", Locale.ENGLISH));
    }

}


