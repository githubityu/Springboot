package com.ityu.study.util;

public class ResultUtil {

    public static <T> ResultBean<T> ok(T t){
        ResultBean<T> tResultBean = new ResultBean<>();
        tResultBean.setCode(0);
        tResultBean.setData(t);
        tResultBean.setMsg("");
        return tResultBean;
    }
}
