package com.ityu.study.controller;

import com.ityu.study.entity.StudentInfoBean;
import com.ityu.study.repositories.StudentInfoBeanRepository;
import com.ityu.study.util.ResultBean;
import com.ityu.study.util.ResultUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentInfoBeanRepository repository;

    @ApiOperation(value = "获取帖子详情")
    @ApiImplicitParams({ // 参数说明
            @ApiImplicitParam(name = "name", required = true,paramType = "query", value = "",dataType = "string"),
            @ApiImplicitParam(name = "sex", required = true,paramType = "query", value = "1",dataType = "int"),
    })
    @PostMapping("/saveST")
     public ResultBean<StudentInfoBean> saveST(String  name, Integer sex){
        StudentInfoBean studentInfoBean = new StudentInfoBean();
        studentInfoBean.setStuName(name);
        studentInfoBean.setSex(sex);
        repository.save(studentInfoBean);
        return ResultUtil.ok(studentInfoBean);
    }

    @ApiOperation(value = "修改名字")
    @ApiImplicitParams({ // 参数说明
            @ApiImplicitParam(name = "name", required = true,paramType = "query", value = "",dataType = "string"),
            @ApiImplicitParam(name = "id", required = true,paramType = "query", value = "1",dataType = "string"),
    })
    @PostMapping("/getST")
    public ResultBean<Integer> getST(String  name, Integer id){
        Integer i = repository.modifyName(name, id);
        return ResultUtil.ok(i);
    }



}
