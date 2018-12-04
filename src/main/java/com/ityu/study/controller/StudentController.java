package com.ityu.study.controller;

import com.ityu.study.annotation.ResponseBodyWrapper;
import com.ityu.study.entity.StudentInfoBean;
import com.ityu.study.repositories.StudentInfoBeanRepository;
import com.ityu.study.util.R;
import com.ityu.study.util.RUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
@ResponseBodyWrapper
public class StudentController {

    private final StudentInfoBeanRepository repository;

    public StudentController(StudentInfoBeanRepository repository) {
        this.repository = repository;
    }

    @ApiOperation(value = "获取帖子详情")
    @ApiImplicitParams({ // 参数说明
            @ApiImplicitParam(name = "name", required = true, paramType = "query", value = "姓名", dataType = "string"),
            @ApiImplicitParam(name = "sex", required = true, paramType = "query", value = "1", dataType = "int"),
    })
    @PostMapping("/saveST")
    public R<StudentInfoBean> saveST(String name, Integer sex, Integer stuNum) {
        if (repository.existsByStuNum(stuNum)) {
            return RUtil.error(new StudentInfoBean(), "已存在");
        }
        StudentInfoBean studentInfoBean = new StudentInfoBean().setSex(sex).setStuName(name).setStuNum(stuNum);
        repository.save(studentInfoBean);
        return RUtil.ok(studentInfoBean);
    }
    @ApiOperation(value = "修改名字")
    @ApiImplicitParams({ // 参数说明
            @ApiImplicitParam(name = "name", required = true, paramType = "query", value = "姓名", dataType = "string"),
            @ApiImplicitParam(name = "id", required = true, paramType = "query", value = "学生id", dataType = "string"),
    })
    @PostMapping("/updateName")
    public R<Integer> updateName(String name, Integer id) {
        Integer i = repository.modifyName(name, id);
        return RUtil.ok(i);
    }

    @ApiOperation(value = "获取学生信息")
    @ApiImplicitParams({ // 参数说明
            @ApiImplicitParam(name = "id", required = true, paramType = "query", value = "学生id", dataType = "string"),
    })
    @GetMapping("/getStudentById")
    public R<StudentInfoBean> getStudentById(Integer id) {
        StudentInfoBean i = repository.findById(id).orElseThrow(R.exception(R.Enum.EAZABLE_CODE_NOT_EXIT));
        return RUtil.ok(i);
    }
}
