package com.ityu.study.status;

import lombok.Getter;

/**
 * @author lihe
 */

public enum GlobalStatusEnum {
    /**
     * 正常
     */
    NORMAL(1),


    /**
     * 删除
     */
    DELETE(0);

    @Getter
    private int code;

    GlobalStatusEnum(int code) {
        this.code = code;
    }

    public static boolean isNormal(int status) {
        return NORMAL.getCode() == status;
    }

}
