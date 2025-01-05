package com.example.StudentCourse.service.Impl;

import com.example.StudentCourse.mapper.TeacherMapper;
import com.example.StudentCourse.pojo.Classes;
import com.example.StudentCourse.pojo.CourseSelection;
import com.example.StudentCourse.pojo.Teacher;
import com.example.StudentCourse.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Teacher login(Teacher teacher){
        return teacherMapper.getByStaffIdandPassword(teacher);
    }

    @Override
    public List<Classes> showTeachClass(String teacherId, String semester) {
        return teacherMapper.showTeachClass(teacherId, semester);
    }
}
