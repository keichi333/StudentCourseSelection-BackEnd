package com.example.StudentCourse.controller;

import com.example.StudentCourse.pojo.*;
import com.example.StudentCourse.service.StudentService;
import com.example.StudentCourse.utils.JwtUtils;
import com.example.StudentCourse.utils.UserContext;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    // 显示所有的课程，并支持搜索功能
    @GetMapping("student/course")
    public Result showClass(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String semester,
            @RequestParam(required = false) String courseId,
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) String staffId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String classTime) {

        // 调用服务层方法，传递分页和搜索条件
        List<Classes> classList = studentService.showClass(page, size, semester, courseId, courseName, staffId, name, classTime);

        return Result.success(classList);
    }

    // 显示该学生选课情况
    @GetMapping("student/selection")
    public Result showSelection(@RequestParam(required = false) String semester) {
        // 从线程变量UserContext中获取 student_id
        String studentId = UserContext.getUser();

        // 调用服务层方法，传递学期参数（如果有）
        List<CourseSelection> selectionList = studentService.showSelection(studentId, semester);

        return Result.success(selectionList);
    }

    // 根据选课信息选课
    @PostMapping("student/choose")
    public Result chooseClass(@RequestBody Classes course){
        // 从请求头中获取 Authorization 字段 并拿出其中的student_id
        String studentId = UserContext.getUser();

        // 判断选择的课程是否有冲突
        Integer choose = studentService.chooseClass(studentId, course);
        if(choose == 3){
            return Result.success();
        }
        else if(choose == 2){
            return Result.error("上课时间冲突");
        }
        else{
            return Result.error("已选过该课程");
        }
    }

    @DeleteMapping("student/delete")
    public Result deleteClass(@RequestBody CourseSelection course){
        String studentId = UserContext.getUser();
        studentService.deleteClass(studentId, course);
        return Result.success();
    }

    @GetMapping("student/info")
    // 显示学生个人信息
    public Result showInfo(){
        String studentId = UserContext.getUser();
        Student stu = studentService.showInfo(studentId);
        return Result.success(stu);
    }

    @PutMapping("student/updateinfo")
    public Result updateInfo(@RequestBody Student stu){

        studentService.updateinfo(stu);
        return Result.success();
    }

    @PutMapping("student/updatepassword")
    public Result updatePassword(@RequestBody Password password){
        String studentId = UserContext.getUser();

        if(!password.getNewPassword().equals(password.getConfirmPassword())){
            return Result.error("新密码与确认密码不匹配！请重试");
        }

        if(!studentService.isPasswordEqual(studentId, password.getCurrentPassword())){
            return Result.error("当前密码错误！请重试");
        }
        else{
            studentService.updatePassword(studentId, password.getNewPassword());
        }
        return Result.success();


    }

}
