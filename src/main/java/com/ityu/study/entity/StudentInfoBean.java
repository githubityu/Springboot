package com.ityu.study.entity;




import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
* show table status from test
 * alter table table_name engine=innodb;
 * 参看引擎 MyISAM与InnoDB
* */
@Entity
@Data
@ApiModel(value="StudentInfoBean",description="学生信息")
public class StudentInfoBean  implements Serializable {

    /**
     * 学号,唯一不重复
     * 声明主键
     * 声明主键的生成策略
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer stuNum;

    /**
     * 学生姓名
     */
    @ApiModelProperty(value="用户名字",name="stuName",example="张三")
    @Column(length = 20)
    private String stuName;

    @ApiModelProperty(value="用户性别",name="sex",example="1")
    @Column(length = 1)
    private Integer sex;

}
