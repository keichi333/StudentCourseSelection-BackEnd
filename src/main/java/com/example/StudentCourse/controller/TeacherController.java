package com.example.StudentCourse.controller;

import com.example.StudentCourse.pojo.Classes;
import com.example.StudentCourse.pojo.CourseSelection;
import com.example.StudentCourse.pojo.Result;
import com.example.StudentCourse.service.TeacherService;
import com.example.StudentCourse.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeacherController {

    @Autowired
    private TeacherService teacherService;


    @GetMapping("/teacher/course")
    public Result showTeachClass(@RequestParam(required = false) String semester){
        String teacherId = UserContext.getUser();

        List<Classes> teachList = teacherService.showTeachClass(teacherId, semester);
        return Result.success(teachList);
    }

}
