package com.example.StudentCourse.controller;

import com.example.StudentCourse.pojo.*;
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
    public Result updateScores(@RequestBody UpdateScoresRequest request){
        // 从请求中获取学生成绩列表和比例
        List<CourseSelection> studentList = request.getStudentList();
        Proportion proportion = request.getProportion();

        String teacherId = UserContext.getTeacher(); // 从上下文获取教师 ID
        teacherService.updateScores(studentList, proportion, teacherId); // 调用服务层处理逻辑

        return Result.success(); // 返回成功响应
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
