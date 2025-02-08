package com.example.StudentCourse.service;

import com.example.StudentCourse.pojo.*;

import java.util.List;

public interface AdminService {
    Admin login(Admin admin);

    Student showInfo(String adminId);

    boolean isPasswordEqual(String adminId, String currentPassword);

    void updatePassword(String adminId, String newPassword);

    String getCurrentSemester();

    void updateSemester(String semester);

    PageResult2 showStudentList(int page, int size, String studentId, String name, String sex, String dateOfBirth, String nativePlace, String mobilePhone, String deptName);

    void updateStudent(String studentId, Student student);

    List<String> showDeptList();

    void deleteStudent(String studentId);

    void resetPassword(String studentId);

    void addStudent(Student student);

    PageResult3 showStudentCourseList(int page, int size, String semester, String studentId, String name, String courseId, String courseName, String classId, String staffId, String staffName, String credit, String classTime);

    void deleteClass(String studentId, String courseId, String classId, String semester);

    List<Classes> showCourseList(String semester, String courseId);
}
