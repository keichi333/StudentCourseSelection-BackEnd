package com.example.StudentCourse.controller;

import com.example.StudentCourse.pojo.*;
import com.example.StudentCourse.service.StudentService;
import com.example.StudentCourse.utils.JwtUtils;
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
    public Result showSelection(HttpServletRequest request,
                                @RequestParam(required = false) String semester) {
        // 从请求头中获取 Authorization 字段 并拿出其中的student_id
        String authorizationHeader = request.getHeader("Authorization");
        String jwtToken = authorizationHeader.substring(7);  // 去除 "Bearer " 前缀
        Claims claims = JwtUtils.parseJWT(jwtToken);  // 解析JWT

        String studentId = claims.get("student_id", String.class);  // 从 claims 中获取 student_id

        // 调用服务层方法，传递学期参数（如果有）
        List<CourseSelection> selectionList = studentService.showSelection(studentId, semester);

        return Result.success(selectionList);
    }

    // 根据选课信息选课
    @PostMapping("student/choose")
    public Result chooseClass(HttpServletRequest request,
                              @RequestBody Classes course){
        // 从请求头中获取 Authorization 字段 并拿出其中的student_id
        String authorizationHeader = request.getHeader("Authorization");
        String jwtToken = authorizationHeader.substring(7);  // 去除 "Bearer " 前缀
        Claims claims = JwtUtils.parseJWT(jwtToken);  // 解析JWT
        String studentId = claims.get("student_id", String.class);  // 从 claims 中获取 student_id

        // 判断选择的课程是否有冲突
        Boolean choose = studentService.chooseClass(studentId, course);
        if(choose){
            return Result.success();
        }
        else{
            return Result.error("上课时间冲突");
        }
    }

    @DeleteMapping("student/delete")
    public Result deleteClass(HttpServletRequest request,
                              @RequestBody CourseSelection course){
        // 从请求头中获取 Authorization 字段 并拿出其中的student_id
        String authorizationHeader = request.getHeader("Authorization");
        String jwtToken = authorizationHeader.substring(7);  // 去除 "Bearer " 前缀
        Claims claims = JwtUtils.parseJWT(jwtToken);  // 解析JWT
        String studentId = claims.get("student_id", String.class);  // 从 claims 中获取 student_id


        studentService.deleteClass(studentId, course);
        return Result.success();
    }

    @GetMapping("student/info")
    // 显示学生个人信息
    public Result showInfo(HttpServletRequest request){
        // 从请求头中获取 Authorization 字段 并拿出其中的student_id
        String authorizationHeader = request.getHeader("Authorization");
        String jwtToken = authorizationHeader.substring(7);  // 去除 "Bearer " 前缀
        Claims claims = JwtUtils.parseJWT(jwtToken);  // 解析JWT
        String studentId = claims.get("student_id", String.class);  // 从 claims 中获取 student_id

        Student stu = studentService.showInfo(studentId);
        return Result.success(stu);
    }

    @PutMapping("student/updateinfo")
    public Result updateInfo(@RequestBody Student stu){

        studentService.updateinfo(stu);
        return Result.success();
    }

    @PutMapping("student/updatepassword")
    public Result updatePassword(HttpServletRequest request,
                                 @RequestBody Password password){
        // 从请求头中获取 Authorization 字段 并拿出其中的student_id
        String authorizationHeader = request.getHeader("Authorization");
        String jwtToken = authorizationHeader.substring(7);  // 去除 "Bearer " 前缀
        Claims claims = JwtUtils.parseJWT(jwtToken);  // 解析JWT
        String studentId = claims.get("student_id", String.class);  // 从 claims 中获取 student_id

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
