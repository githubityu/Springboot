package com.ityu.study.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="ResultBean",description="统一数据信息")
public class ResultBean<T> {
    @ApiModelProperty(value="状态吗",name="code",example="0")
    public int code;
    @ApiModelProperty(value="错误信息",name="msg",example="密码不对")
    public String msg;
    @ApiModelProperty(value="对象信息",name="data",example="")
    public T data;

}
