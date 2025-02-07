package com.example.StudentCourse.controller;

import com.example.StudentCourse.pojo.*;
import com.example.StudentCourse.service.AdminService;
import com.example.StudentCourse.service.StudentService;
import com.example.StudentCourse.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    // **************************
    // **************************
    // 标头的一些操作

    // 显示管理员个人信息
    @GetMapping("admin/info")
    public Result showInfo(){
        String adminId = UserContext.getUser();
        Student adm = adminService.showInfo(adminId);
        return Result.success(adm);
    }

    // 更新密码
    @PutMapping("admin/updatepassword")
    public Result updatePassword(@RequestBody Password password){
        String adminId = UserContext.getUser();

        if(!password.getNewPassword().equals(password.getConfirmPassword())){
            return Result.error("新密码与确认密码不匹配！请重试");
        }

        if(!adminService.isPasswordEqual(adminId, password.getCurrentPassword())){
            return Result.error("当前密码错误！请重试");
        }
        else{
            adminService.updatePassword(adminId, password.getNewPassword());
        }
        return Result.success();
    }

    // 获取当前学期
    @GetMapping("admin/semester")
    public Result getCurrentSemester(){
        String semester = adminService.getCurrentSemester();
        return Result.success(semester);
    }

    // 更新当前学期
    @PutMapping("admin/updatesemester")
    public Result updateSemester(@RequestParam String semester) {
        String adminId = UserContext.getUser();
        adminService.updateSemester(semester);
        return Result.success();
    }


    // **************************
    // **************************
    // 学生信息管理的一些操作

    // 显示所有的课程，并支持搜索功能
    @GetMapping("admin/studentlist")
    public Result showClass(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String sex,
            @RequestParam(required = false) String dateOfBirth,
            @RequestParam(required = false) String nativePlace,
            @RequestParam(required = false) String mobilePhone,
            @RequestParam(required = false) String deptName) {

        PageResult2 pageResult = adminService.showStudentList(page, size, studentId, name, sex, dateOfBirth, nativePlace, mobilePhone, deptName);

        // 返回包含总条数和课程列表的结果
        return Result.success(pageResult);
    }

    // 更新学生信息
    @PutMapping("admin/student/{studentId}")
    public Result updateStudent(@PathVariable String studentId, @RequestBody Student student){
        adminService.updateStudent(studentId, student);
        return Result.success();
    }

    // 显示所有的院系列表
    @GetMapping("admin/deptlist")
    public Result showDeptList(){
        List<String> deptList = adminService.showDeptList();
        return Result.success(deptList);
    }

    // 删除学生信息
    @DeleteMapping("admin/student/{studentId}")
    public Result deleteStudent(@PathVariable String studentId){
        adminService.deleteStudent(studentId);
        return Result.success();
    }

    // 重置密码
    @PutMapping("admin/student/passwordreset/{studentId}")
    public Result resetPassword(@PathVariable String studentId){
        adminService.resetPassword(studentId);
        return Result.success();
    }

    // 新增学生信息
    @PostMapping("admin/student")
    public Result addStudent(@RequestBody Student student){
        adminService.addStudent(student);
        return Result.success();
    }



}
