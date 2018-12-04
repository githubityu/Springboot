package com.ityu.study.bo;

import lombok.Data;

/**
 * 消息队列封装
 *
 * @author lihe
 */
@Data
public class OrderMessageBO {


    private Integer id;


    private Integer status;


    private String serviceName;


    private String subServiceName;

    private Integer serviceId;


    private Integer userId;


    private Integer supplierId;


    private String postalCode;

    /**
     * 服务端历史详情id
     */
    private Integer hid;


}
