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


    // **************************
    // **************************
    // 教师信息管理的一些操作

    // 显示所有教师信息，并支持搜索功能
    @GetMapping("admin/teacherlist")
    public Result showTeacherList(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String staffId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String sex,
            @RequestParam(required = false) String dateOfBirth,
            @RequestParam(required = false) String professionalRanks,
            @RequestParam(required = false) String salary,
            @RequestParam(required = false) String deptName) {

        PageResult4 pageResult = adminService.showTeacherList(page, size, staffId, name, sex, dateOfBirth, professionalRanks, salary, deptName);

        // 返回包含总条数和课程列表的结果
        return Result.success(pageResult);
    }

    // 删除教师信息
    @DeleteMapping("admin/teacher/{staffId}")
    public Result deleteTeacher(@PathVariable String staffId){
        adminService.deleteTeacher(staffId);
        return Result.success();
    }

    // 更新教师信息
    @PutMapping("admin/teacher/{staffId}")
    public Result updateTeacher(@PathVariable String staffId, @RequestBody Teacher teacher){
        adminService.updateTeacher(staffId, teacher);
        return Result.success();
    }

    // 重置密码
    @PutMapping("admin/teacher/passwordreset/{staffId}")
    public Result resetTeacherPassword(@PathVariable String staffId){
        adminService.resetTeacherPassword(staffId);
        return Result.success();
    }

    // 新增教师信息
    @PostMapping("admin/teacher")
    public Result addTeacher(@RequestBody Teacher teacher){
        adminService.addTeacher(teacher);
        return Result.success();
    }

    // 显示所有教师课程，并支持搜索功能
    @GetMapping("admin/teachercourselist")
    public Result showTeacherSelection(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String semester,
            @RequestParam(required = false) String staffId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String courseId,
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) String classId,
            @RequestParam(required = false) String classTime) {

        PageResult5 pageResult = adminService.showTeacherCourseList(page, size, semester, staffId, name, courseId, courseName, classId, classTime);

        // 返回包含总条数和课程列表的结果
        return Result.success(pageResult);
    }

    // 删除教师课程安排
    @DeleteMapping("admin/teachercourse")
    public Result deleteTeacherClass(@RequestParam String staffId, @RequestParam String courseId, @RequestParam String classId, @RequestParam String semester) {
        adminService.deleteTeacherClass(staffId, courseId, classId, semester);
        return Result.success();
    }

    // 查询所有已有课程
    @GetMapping("admin/allcourses")
    public Result getAllCourses(@RequestParam(required = false) String courseId) {
        List<Course> courseList = adminService.getAllCourses(courseId);
        return Result.success(courseList);
    }

    // 显示某具体教师课程安排情况
    @GetMapping("admin/teachercourse")
    public Result getTeacherCourse(@RequestParam String staffId, @RequestParam String semester) {
        List<Classes> courseList = adminService.getTeacherCourse(staffId, semester);
        return Result.success(courseList);
    }

    // 为某具体教师安排课程
    @PostMapping("admin/teachercourse")
    public Result addTeacherCourse(@RequestParam String semester,
                                   @RequestParam String courseId,
                                   @RequestParam String staffId,
                                   @RequestParam String classTime,
                                   @RequestParam String maxStudents) {
        // 判断选择的课程是否有冲突
        Integer choose = adminService.chooseClass(staffId, semester,courseId, classTime, maxStudents);
        if(choose==3){
            return Result.success();
        }
        else if(choose==2){
            return Result.error("上课时间冲突！请重新选择");
        }
        else{
            return Result.error("已安排过该门课程！请勿重复选择");
        }

    }

    // **************************
    // **************************
    // 课程信息管理的一些操作

    // 显示所有课程信息，并支持搜索功能
    @GetMapping("admin/courselist")
    public Result showCourseList(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String courseId,
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) String credit,
            @RequestParam(required = false) String creditHours,
            @RequestParam(required = false) String deptName) {

        PageResult6 pageResult = adminService.showOriginCourseList(page, size, courseId, courseName, credit, creditHours, deptName);

        // 返回包含总条数和课程列表的结果
        return Result.success(pageResult);
    }

    // 更新课程信息
    @PutMapping("admin/course/{courseId}")
    public Result updateCourse(@PathVariable String courseId, @RequestBody Course course){
        adminService.updateCourse(courseId, course);
        return Result.success();
    }

    // 删除课程信息
    @DeleteMapping("admin/course/{courseId}")
    public Result deleteCourse(@PathVariable String courseId) {
        adminService.deleteCourse(courseId);
        return Result.success();
    }

    // 新增课程信息
    @PostMapping("admin/course")
    public Result addCourse(@RequestBody Course course){
        adminService.addCourse(course);
        return Result.success();
    }

}
