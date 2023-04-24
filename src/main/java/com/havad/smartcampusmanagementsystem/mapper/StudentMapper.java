package com.havad.smartcampusmanagementsystem.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.havad.smartcampusmanagementsystem.pojo.Admin;
import com.havad.smartcampusmanagementsystem.pojo.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentMapper extends BaseMapper<Student> {
}
