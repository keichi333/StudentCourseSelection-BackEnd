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

    @Autowired
    private StudentService studentService;

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

    // 显示所有学生信息，并支持搜索功能
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

    // 显示所有学生选课，并支持搜索功能
    @GetMapping("admin/studentcourselist")
    public Result showSelection(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String semester,
            @RequestParam(required = false) String studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String courseId,
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) String classId,
            @RequestParam(required = false) String staffId,
            @RequestParam(required = false) String staffName,
            @RequestParam(required = false) String credit,
            @RequestParam(required = false) String classTime) {

        PageResult3 pageResult = adminService.showStudentCourseList(page, size, semester, studentId, name, courseId, courseName, classId, staffId, staffName, credit, classTime);

        // 返回包含总条数和课程列表的结果
        return Result.success(pageResult);
    }

    // 删除学生选课
    @DeleteMapping("admin/studentcourse")
    public Result deleteClass(@RequestParam String studentId, @RequestParam String courseId, @RequestParam String classId, @RequestParam String semester) {
        adminService.deleteClass(studentId, courseId, classId, semester);
        return Result.success();
    }

    // 显示某具体学生选课情况
    @GetMapping("admin/selection")
    public Result showStudentSelection(@RequestParam String studentId, @RequestParam String semester) {

        // 调用服务层方法，传递学期参数（如果有）
        List<CourseSelection> selectionList = studentService.showSelection(studentId, semester);

        return Result.success(selectionList);
    }

    // 显示所有课程
    @GetMapping("admin/courses")
    public Result showCourseList(@RequestParam String semester,
                                 @RequestParam(required = false) String courseId) {

        List<Classes> courseList = adminService.showCourseList(semester, courseId);
        return Result.success(courseList);
    }

    // 为某具体学生选课
    @PostMapping("admin/studentcourse")
    public Result addCourse(@RequestParam String studentId,
                            @RequestParam String semester,
                            @RequestParam String courseId,
                            @RequestParam String staffId,
                            @RequestParam String classId,
                            @RequestParam String classTime) {
        Classes course = new Classes();
        course.setSemester(semester);
        course.setCourseId(courseId);
        course.setStaffId(staffId);
        course.setClassId(classId);
        course.setClassTime(classTime);

        // 判断选择的课程是否有冲突
        Integer choose = studentService.chooseClass(studentId, course);
        if(choose==3){
            return Result.success();
        }
        else if(choose==2){
            return Result.error("上课时间冲突！请重新选择");
        }
        else{
            return Result.error("已选过该门课程！请勿重复选择");
        }

    }


}
