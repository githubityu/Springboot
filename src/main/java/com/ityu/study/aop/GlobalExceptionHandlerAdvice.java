package com.ityu.study.aop;



import com.ityu.study.exception.CommonException;
import com.ityu.study.util.R;
import com.ityu.study.util.RUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;


/**
 * 统一异常处理
 *
 * @author lihe
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandlerAdvice {


    /**
     * 用于捕获通用异常并返回错误代码
     *
     * @param e 异常
     * @return 封装后的异常响应
     */
    @ResponseBody
    @ExceptionHandler({CommonException.class})
    public R commonExceptionHandler(CommonException e) {
        log.error("通用异常:" + e.getMessageEnum().getMessage() + ":" + e.getMessageEnum().getCode());
        return RUtil.error(e.getMessageEnum());
    }


    /**
     * 捕获自定义运行时异常
     *
     * @param e 异常
     * @return 封装后的异常响应
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public R validationExceptionExHandler(MethodArgumentNotValidException e) {


        StringBuilder emg = new StringBuilder("Invalid Request:");

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            emg.append(fieldError.getDefaultMessage()).append(", ");
        }

        log.error("数据绑定异常:", e);
        return RUtil.error(-9999, emg.toString());
    }




    /**
     * 客户端提交了不合法的参数
     *
     * @param e 异常
     * @return 封装后的异常响应
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({ConstraintViolationException.class})
    public R constraintViolationExceptionExHandler(ConstraintViolationException e) {
        return RUtil.error(e.getMessage());
    }




}
