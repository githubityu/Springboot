package com.ityu.study.repositories;

import com.ityu.study.entity.StudentInfoBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface   StudentInfoBeanRepository extends JpaRepository<StudentInfoBean, Integer> {


    @Transactional
    @Modifying
    @Query("update StudentInfoBean set stuName =?1 where stuNum =?2")
    public int modifyName(String stu_name,Integer stu_num);
}
