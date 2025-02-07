package com.example.StudentCourse.service;

import com.example.StudentCourse.pojo.Admin;
import com.example.StudentCourse.pojo.PageResult2;
import com.example.StudentCourse.pojo.Student;
import com.example.StudentCourse.pojo.Teacher;

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
}
