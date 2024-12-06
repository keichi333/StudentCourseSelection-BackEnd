package com.example.StudentCourse.controller;

import com.example.StudentCourse.pojo.Result;
import com.example.StudentCourse.pojo.Student;
import com.example.StudentCourse.service.StudentService;
import com.example.StudentCourse.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/login")
    public Result Login(@RequestBody Student stu){
        Student s = studentService.login(stu);
        if(s!=null){
            Map<String, Object> claims=new HashMap<>();
            claims.put("student_id",s.getStudentId());
            claims.put("name",s.getName());

            String jwt=JwtUtils.generateJwt(claims);   // Jwt中包含了当前登录的学生信息
            return Result.success(jwt);
        }

        return Result.error("用户名或密码错误");
    }

}
