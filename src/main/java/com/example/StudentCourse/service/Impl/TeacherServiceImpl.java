package com.example.StudentCourse.service.Impl;

import com.example.StudentCourse.mapper.TeacherMapper;
import com.example.StudentCourse.pojo.Teacher;
import com.example.StudentCourse.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Teacher login(Teacher teacher){
        return teacherMapper.getByStaffIdandPassword(teacher);
    }
}
