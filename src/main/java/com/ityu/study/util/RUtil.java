package com.ityu.study.util;

public class RUtil {

    public static <T> R<T> ok(T t){
        R<T> tR = new R<>();
        tR.setCode(0);
        tR.setData(t);
        tR.setMsg("");
        return tR;
    }

    public static <T> R<T> error(T t, R.Enum msg){
        R<T> tR = new R<>();
        tR.setCode(msg.getCode());
        tR.setData(t);
        tR.setMsg(msg.getMessage());
        return tR;
    }
    public static <T> R<T> error(T t, String msg){
        R<T> tR = new R<>();
        tR.setCode(-1);
        tR.setData(t);
        tR.setMsg(msg);
        return tR;
    }
    public static  R error(R.Enum msg){
        R tR = new R();
        tR.setCode(msg.getCode());
        tR.setMsg(msg.getMessage());
        return tR;
    }

    public static  R error(int code,String msg){
        R tR = new R();
        tR.setCode(code);
        tR.setMsg(msg);
        return tR;
    }

    public static  R error(String msg){
        R tR = new R();
        tR.setCode(-1);
        tR.setMsg(msg);
        return tR;
    }
}
