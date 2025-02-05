package com.example.StudentCourse.service;

import com.example.StudentCourse.pojo.*;

import java.util.List;

public interface StudentService {
    // 学生登录
    Student login(Student stu);

    // 查看课程，支持分页和搜索功能
    PageResult showClass(int page, int size, String semester, String courseId, String courseName,
                         String staffId, String name, String classTime);

    // 查看选课情况
    List<CourseSelection> showSelection(String studentId, String semester);

    // 选择课程
    Integer chooseClass(String studentId, Classes course);

    // 退课
    void deleteClass(String studentId, CourseSelection course);

    // 显示个人信息
    Student showInfo(String studentId);

    // 修改个人信息
    void updateinfo(Student stu);

    // 判断密码是否正确
    boolean isPasswordEqual(String studentId, String currentPassword);

    // 修改密码
    void updatePassword(String studentId, String newPassword);

    // 查看通知列表
    List<Email> showEmailList(String studentId);

    // 获取当前学期
    String getCurrentSemester();
}

