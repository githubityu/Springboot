package com.ityu.study.function;

/**
 * @author lihe
 */
@FunctionalInterface
public interface CopyProcess<S, T> {
    /**
     * 对传入的对象进行加工，并将目标对象返回
     *
     * @param s 源头
     * @param t 目标值
     * @return 处理后的对象
     */
    T process(S s, T t);
}
