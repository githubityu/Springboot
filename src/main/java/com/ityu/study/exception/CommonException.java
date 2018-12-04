package com.ityu.study.exception;


import com.ityu.study.util.R;
import lombok.Getter;

/**
 * @author lihe
 */
public class CommonException extends RuntimeException {
    private static final long serialVersionUID = 3479235589333240055L;
    @Getter
    private R.Enum messageEnum;
    @Getter
    private String message;


    public CommonException(R.Enum messageEnum) {

        this.messageEnum = messageEnum;
        this.message = messageEnum.getMessage();
    }

    public CommonException(String message) {
        super(message);
    }



}
