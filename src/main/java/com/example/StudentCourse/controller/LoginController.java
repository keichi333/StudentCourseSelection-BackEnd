package com.example.StudentCourse.controller;

import com.example.StudentCourse.pojo.Admin;
import com.example.StudentCourse.pojo.Result;
import com.example.StudentCourse.pojo.Student;
import com.example.StudentCourse.pojo.Teacher;
import com.example.StudentCourse.service.AdminService;
import com.example.StudentCourse.service.StudentService;
import com.example.StudentCourse.service.TeacherService;
import com.example.StudentCourse.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public Result Login(@RequestBody Student stu){
        Student s = studentService.login(stu);
        if(s!=null){
            Map<String, Object> claims=new HashMap<>();
            claims.put("role","student");
            claims.put("Id",s.getStudentId());
            claims.put("name",s.getName());

            String jwt=JwtUtils.generateJwt(claims);   // Jwt中包含了当前登录的学生信息
            return Result.success(jwt);
        }

        return Result.error("用户名或密码错误");
    }

    @PostMapping("/login/teacher")
    public Result LoginTeacher(@RequestBody Teacher teacher){
        Teacher t = teacherService.login(teacher);
        if(t!=null){
            Map<String, Object> claims=new HashMap<>();
            claims.put("role","teacher");
            claims.put("Id",t.getStaffId());
            claims.put("name",t.getName());

            String jwt=JwtUtils.generateJwt(claims);
            return Result.success(jwt);
        }

        return Result.error("用户名或密码错误");
    }

    @PostMapping("/login/admin")
    public Result LoginAdmin(@RequestBody Admin admin){
        Admin a = adminService.login(admin);
        if(a!=null){
            Map<String, Object> claims=new HashMap<>();
            claims.put("role","admin");
            claims.put("Id",a.getAdminId());
            claims.put("name",a.getName());

            String jwt=JwtUtils.generateJwt(claims);
            return Result.success(jwt);
        }

        return Result.error("用户名或密码错误");
    }

}
