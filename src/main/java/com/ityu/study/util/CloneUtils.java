package com.ityu.study.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 对象克隆工具类
 *
 * @author lihe
 */
@SuppressWarnings("unchecked")
@Slf4j
@UtilityClass
public class CloneUtils {

    /**
     * 无需进行复制的特殊类型数组
     */
    private final static Class[] needlessCloneClasses = new Class[]{String.class, Boolean.class, Character.class, Byte.class, Short.class,
            Integer.class, Long.class, Float.class, Double.class, Void.class, Object.class, Class.class
    };

    /**
     * 判断该类型对象是否无需复制
     *
     * @param c 指定类型
     * @return 如果不需要复制则返回真，否则返回假
     */
    private static boolean isNeedlessClone(Class c) {
        //基本类型
        if (c.isPrimitive()) {
            return true;
        }
        //是否在无需复制类型数组里
        for (Class tmp : needlessCloneClasses) {
            if (c.equals(tmp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 尝试创建新对象
     *
     * @param value 原始对象
     * @return 新的对象
     */
    private static Object createObject(Object value) throws IllegalAccessException {
        try {
            return value.getClass().getConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException ignored) {
        }
        return null;
    }

    /**
     * 复制对象数据
     *
     * @param value 原始对象
     * @param level 复制深度。小于0为无限深度，即将深入到最基本类型和Object类级别的数据复制；
     *              大于0则按照其值复制到指定深度的数据，等于0则直接返回对象本身而不进行任何复制行为。
     * @return 返回复制后的对象
     */
    private static Object clone(Object value, int level) throws IllegalAccessException, InstantiationException {
        if (value == null) {
            return null;
        }
        if (level == 0) {
            return value;
        }
        Class c = value.getClass();
        if (isNeedlessClone(c)) {
            return value;
        }
        level--;
        //复制新的集合
        if (value instanceof Collection) {
            Collection tmp = (Collection) c.newInstance();
            for (Object v : (Collection) value) {
                //深度复制
                tmp.add(clone(v, level));
            }
            value = tmp;
            //复制新的Array
        } else if (c.isArray()) {
            value = cloneBaseArray(value, level, c);
            //复制新的MAP
        } else if (value instanceof Map) {
            Map tmp = (Map) c.newInstance();
            Map org = (Map) value;
            for (Object key : org.keySet()) {
                //深度复制
                tmp.put(key, clone(org.get(key), level));
            }
            value = tmp;
        } else {
            Object tmp = createObject(value);
            //无法创建新实例则返回对象本身，没有克隆
            if (tmp == null) {
                return value;
            }
            Set<Field> fields = new HashSet<>();
            while (c != null && !c.equals(Object.class)) {
                fields.addAll(Arrays.asList(c.getDeclaredFields()));
                c = c.getSuperclass();
            }
            for (Field field : fields) {
                //仅复制非final字段
                if (!Modifier.isFinal(field.getModifiers())) {
                    field.setAccessible(true);
                    //深度复制
                    field.set(tmp, clone(field.get(value), level));
                }
            }
            value = tmp;
        }
        return value;
    }

    private static Object cloneBaseArray(Object value, int level, Class c) throws IllegalAccessException, InstantiationException {
        //首先判断是否为基本数据类型
        if (c.equals(int[].class)) {
            int[] old = (int[]) value;
            value = Arrays.copyOf(old, old.length);
        } else if (c.equals(short[].class)) {
            short[] old = (short[]) value;
            value = Arrays.copyOf(old, old.length);
        } else if (c.equals(char[].class)) {
            char[] old = (char[]) value;
            value = Arrays.copyOf(old, old.length);
        } else if (c.equals(float[].class)) {
            float[] old = (float[]) value;
            value = Arrays.copyOf(old, old.length);
        } else if (c.equals(double[].class)) {
            double[] old = (double[]) value;
            value = Arrays.copyOf(old, old.length);
        } else if (c.equals(long[].class)) {
            long[] old = (long[]) value;
            value = Arrays.copyOf(old, old.length);
        } else if (c.equals(boolean[].class)) {
            boolean[] old = (boolean[]) value;
            value = Arrays.copyOf(old, old.length);
        } else if (c.equals(byte[].class)) {
            byte[] old = (byte[]) value;
            value = Arrays.copyOf(old, old.length);
        } else {
            Object[] old = (Object[]) value;
            Object[] tmp = Arrays.copyOf(old, old.length, old.getClass());
            for (int i = 0; i < old.length; i++) {
                tmp[i] = clone(old[i], level);
            }
            value = tmp;
        }
        return value;
    }

    /**
     * 浅表复制对象
     *
     * @param value 原始对象
     * @return 复制后的对象，只复制一层
     */
    public static Object clone(Object value) {
        try {
            return clone(value, 1);
        } catch (Exception e) {
            log.error("Object clone failure!", e);
        }
        throw new RuntimeException();
    }

    /**
     * 深度复制对象
     *
     * @param value 原始对象
     * @return 复制后的对象
     */
    public static Object deepClone(Object value) {
        try {
            return clone(value, -1);
        } catch (Exception e) {
            log.error("Object clone failure!", e);
        }
        throw new RuntimeException();
    }


}
