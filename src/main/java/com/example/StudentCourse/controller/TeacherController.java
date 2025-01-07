package com.example.StudentCourse.controller;

import com.example.StudentCourse.pojo.Classes;
import com.example.StudentCourse.pojo.CourseSelection;
import com.example.StudentCourse.pojo.Result;
import com.example.StudentCourse.pojo.scoreDistribution;
import com.example.StudentCourse.service.TeacherService;
import com.example.StudentCourse.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    // 查看教师本学期教的所有课程
    @GetMapping("/teacher/course")
    public Result showTeachClass(@RequestParam(required = false) String semester,
                                  @RequestParam(required = false) String courseId){
        String teacherId = UserContext.getTeacher();

        List<Classes> teachList = teacherService.showTeachClass(teacherId, semester, courseId);
        return Result.success(teachList);
    }

    // 查看教师本学期教某门课程的所有学生的成绩
    @GetMapping("/teacher/studentScoreList")
    public Result showStuentScore(@RequestParam(required = false) String semester,
                                  @RequestParam(required = false) String courseId,
                                  @RequestParam(required = false) String classId){
        String teacherId = UserContext.getTeacher();
        List<CourseSelection> studentScoreList = teacherService.showStuentScore(teacherId, semester, courseId, classId);
        return Result.success(studentScoreList);
    }

    // 更新学生的成绩
    @PutMapping("/teacher/updateScores")
    public Result updateScores(@RequestBody List<CourseSelection> studentScoreList){
        String teacherId = UserContext.getTeacher();
        teacherService.updateScores(studentScoreList, teacherId);
        return Result.success();
    }

    // 查看学生的成绩分布
    @GetMapping("/teacher/ScoreDistribution")
    public Result ScoreDistribution(@RequestParam(required = false) String semester,
                                    @RequestParam(required = false) String courseId,
                                    @RequestParam(required = false) String classId){
        String teacherId = UserContext.getTeacher();
        List<scoreDistribution> scoreDistributionList = teacherService.ScoreDistribution(teacherId, semester, courseId, classId);
        return Result.success(scoreDistributionList);
    }
    @GetMapping("/teacher/NormalScoreDistribution")
    public Result  NormalScoreDistribution(@RequestParam(required = false) String semester,
                                    @RequestParam(required = false) String courseId,
                                    @RequestParam(required = false) String classId){
        String teacherId = UserContext.getTeacher();
        List<scoreDistribution> normalScoreDistributionList = teacherService.NormalScoreDistribution(teacherId, semester, courseId, classId);
        return Result.success(normalScoreDistributionList);
    }
    @GetMapping("/teacher/TestScoreDistribution")
    public Result  TestScoreDistribution(@RequestParam(required = false) String semester,
                                           @RequestParam(required = false) String courseId,
                                           @RequestParam(required = false) String classId){
        String teacherId = UserContext.getTeacher();
        List<scoreDistribution> testScoreDistributionList = teacherService.TestScoreDistribution(teacherId, semester, courseId, classId);
        return Result.success(testScoreDistributionList);
    }


}
