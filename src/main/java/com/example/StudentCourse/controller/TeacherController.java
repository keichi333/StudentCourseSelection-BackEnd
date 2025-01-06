package com.example.StudentCourse.controller;

import com.example.StudentCourse.pojo.Classes;
import com.example.StudentCourse.pojo.CourseSelection;
import com.example.StudentCourse.pojo.Result;
import com.example.StudentCourse.service.TeacherService;
import com.example.StudentCourse.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeacherController {

    @Autowired
    private TeacherService teacherService;


    @GetMapping("/teacher/course")
    public Result showTeachClass(@RequestParam(required = false) String semester,
                                  @RequestParam(required = false) String courseId){
        String teacherId = UserContext.getUser();

        List<Classes> teachList = teacherService.showTeachClass(teacherId, semester, courseId);
        return Result.success(teachList);
    }

    @GetMapping("/teacher/studentScoreList")
    public Result showStuentScore(@RequestParam(required = false) String semester,
                                  @RequestParam(required = false) String courseId,
                                  @RequestParam(required = false) String classId){
        String teacherId = UserContext.getUser();
        List<CourseSelection> studentScoreList = teacherService.showStuentScore(teacherId, semester, courseId, classId);
        return Result.success(studentScoreList);
    }

    @PutMapping("/teacher/updateScores")
    public Result updateScores(@RequestBody List<CourseSelection> studentScoreList){
        String teacherId = UserContext.getUser();
        teacherService.updateScores(studentScoreList, teacherId);
        return Result.success();
    }


}
