package com.ityu.study.util;

import org.springframework.lang.Nullable;

import java.math.BigDecimal;

/**
 * 新加坡税费计算
 *
 * @author lihe
 */
public class GstUtils {

    /**
     * 计算需要支付的税费
     *
     * @param pay 支付金额
     * @return 应该支付的税
     */
    @Nullable
    public static BigDecimal getGst(BigDecimal pay) {
        return null;
    }

    /**
     * 获取税率
     *
     * @return 当前的税率
     */
    @Nullable
    public static BigDecimal getGstRate() {
        return null;
    }


}
