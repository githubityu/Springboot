package com.ityu.study.util;

import com.ityu.study.exception.CommonException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.function.Supplier;

@Data
@ApiModel(value="R",description="统一数据信息")
public class R<T> implements Serializable{
    @ApiModelProperty(value="状态吗",name="code",example="0")
    public int code;
    @ApiModelProperty(value="错误信息",name="msg",example="密码不对")
    public String msg;
    @ApiModelProperty(value="对象信息",name="data",example="")
    public T data;

    public static Supplier<CommonException> exception(Enum em) {
        return () -> new CommonException(em);
    }
    /**
     * 正数需要进行进一步操作
     * 0 正常业务操作流程
     * 负数出现错误
     */
    public enum Enum {

        /**
         * 请求验证手机号
         */
        DO_VERIFICATION_PHONE(100, "Please "),

        /**
         * 请求验证额外的信息
         */
        SUBMIT_INFO_REQUIRED(101, "Please submit detailed information"),


        /**
         * 操作成功
         */
        SUCCESS(0, "Success"),


        /**
         * 数据访问权限异常
         */
        EX9000(-9000, "Data not access"),

        /**
         *
         */
        EX9999(-9999, "Unknown Err happened"),


        /**
         * 状态异常
         */
        EX9001(-9001, "Can't do that now"),

        /**
         * eazable 账号不存在
         */
        EAZABLE_CODE_NOT_EXIT(-9002, "Eazable code not exist"),
        /**
         * 邮箱已注册
         */
        EMAIL_DUPLICATION(-9003, "This email account already been registered"),

        /**
         * Eazable账号被绑定
         */
        EAZABLE_ACCOUNT_BANDED(-9004, "This Eazable account has been bound"),


        /**
         * 原始数据提交错误
         */
        ORIGINAL_NOT_MATCH(-9004, "The data you submit not match with original"),

        /**
         * 费用未付清
         */
        UNPAID_FEES(-9005, "Please proceed to make payment"),


        /**
         * 账号不存在
         */
        USER_NOT_FOUND(-100, "This account does not exist"),
        /**
         * 账号状态异常
         */
        USER_NOT_ENABLED(-101, "This account been locked"),
        /**
         * 用户未提交密码时通过密码登录
         */
        USER_PASSWORD_NOT_EXIST(-102, "You haven't submit password"),
        /**
         * 密码错误
         */
        USER_PASSWORD_NOT_CORRECT(-103, "Wrong password"),
        /**
         * 注册时用户已经存在
         */
        USER_EXITED(-104, "Account been registered"),
        /**
         * 通过邮箱修改系信息时邮箱不存在
         */
        USER_EMAIL_NOT_EXIT(-105, "This email haven't bean registered"),
        /**
         * 登录状态改变
         */
        USER_TOKEN_FAILURE(-200, "Please login again "),
        /**
         * 多设备登录
         */
        USER_TOKEN_CONFLICT(-201, "This account has been login with other device"),
        /**
         * 验证码错误
         */
        VERIFICATION_ERR(-300, "Your code not correct"),
        /**
         * 验证码过期
         */
        VERIFICATION_OVERDUE(-301, "code expired"),

        ORDER_NOT_EXIT(-400, "Order not exits"),

        DATA_NOT_FOUND(-404, "The data which you need is not exist now,check the id you submitted"),
        /**
         * 权限不足
         */
        EX403(-403, "This function not open for you"),

        /**
         * 投票已经结束
         */
        VOTING_ENDED(-501, "Voting has ended"),
        /**
         * 位置已经预定
         */
        SLOT_BEEN_USED(-502, "Sorry,the slot been used"),

        /**
         * 金额不足
         */
        NOT_SUFFICIENT_FUNDS(-601, "Sorry，your balance is not enough");


        @Getter
        @Setter
        private int code;

        @Getter
        @Setter
        private String message;

        Enum(int code, String message) {
            this.code = code;
            this.message = message;
        }


    }

}
