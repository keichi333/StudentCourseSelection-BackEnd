package com.example.StudentCourse.service.Impl;

import com.example.StudentCourse.mapper.AdminMapper;
import com.example.StudentCourse.mapper.StudentMapper;
import com.example.StudentCourse.mapper.TeacherMapper;
import com.example.StudentCourse.pojo.*;
import com.example.StudentCourse.service.AdminService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Admin login(Admin admin) {
        return adminMapper.getByAdminIdandPassword(admin);
    }

    @Override
    public Student showInfo(String adminId) {
        return adminMapper.selectInfo(adminId);
    }

    @Override
    public boolean isPasswordEqual(String adminId, String currentPassword) {
        String id = adminMapper.findPassword(adminId,currentPassword);
        // 如果没有找到学生记录或者密码不匹配，返回 false
        return id != null;
    }

    @Override
    public void updatePassword(String adminId, String newPassword) {
        adminMapper.updatePassword(adminId,newPassword);
    }

    @Override
    public String getCurrentSemester() {
        return adminMapper.getCurrentSemester();
    }

    // 修改学期
    @Override
    public void updateSemester(String semester) {
        adminMapper.updateSemester(semester);
    }

    @Override
    public PageResult2 showStudentList(int page, int size, String studentId, String name, String sex, String dateOfBirth, String nativePlace, String mobilePhone, String deptName) {
        // 使用 PageHelper 插件进行分页
        PageHelper.startPage(page, size);

        // 调用 Mapper 查询方法，传入搜索条件
        List<Student> studentList = adminMapper.showStudentWithFilters(studentId, name, sex, dateOfBirth, nativePlace, mobilePhone, deptName);
        Page<Student> p = (Page<Student>) studentList;

        return new PageResult2(p.getTotal(), p.getResult());
    }

    @Override
    public void updateStudent(String studentId, Student student) {
        String deptName = student.getDeptName();
        String deptId = adminMapper.selectDeptIdByName(deptName);
        student.setDeptId(deptId);

        adminMapper.updateStudent(studentId, student);
    }

    @Override
    public List<String> showDeptList() {
        return adminMapper.showDeptList();
    }

    @Override
    public void deleteStudent(String studentId) {
        adminMapper.deleteStudent(studentId);
    }

    @Override
    public void resetPassword(String studentId) {
        studentMapper.updatePassword(studentId, "123456");
    }

    @Override
    public void addStudent(Student student) {
        String deptName = student.getDeptName();
        String deptId = adminMapper.selectDeptIdByName(deptName);
        student.setDeptId(deptId);

        studentMapper.addStudent(student);
    }

    @Override
    public PageResult3 showStudentCourseList(int page, int size, String semester, String studentId, String name, String courseId, String courseName, String classId, String staffId, String staffName, String credit, String classTime) {
        // 使用 PageHelper 插件进行分页
        PageHelper.startPage(page, size);

        // 调用 Mapper 查询方法，传入搜索条件
        List<CourseSelection> studentCourseList = adminMapper.showCourseSelectionWithFilters(semester, studentId, name, courseId, courseName, classId, staffId, staffName, credit, classTime);
        Page<CourseSelection> c = (Page<CourseSelection>) studentCourseList;

        return new PageResult3(c.getTotal(), c.getResult());
    }

    @Override
    public void deleteClass(String studentId, String courseId, String classId, String semester) {
        adminMapper.deleteClass(studentId, courseId, classId, semester);
    }

    @Override
    public List<Classes> showCourseList(String semester, String courseId) {
        return studentMapper.showClassWithFilters(semester, courseId, null, null, null, null);
    }

    @Override
    public PageResult4 showTeacherList(int page, int size, String staffId, String name, String sex, String dateOfBirth, String professionalRanks, String salary, String deptName) {
        // 使用 PageHelper 插件进行分页
        PageHelper.startPage(page, size);

        // 调用 Mapper 查询方法，传入搜索条件
        List<Teacher> teacherList = adminMapper.showTeacherWithFilters(staffId, name, sex, dateOfBirth, professionalRanks, salary, deptName);
        Page<Teacher> t = (Page<Teacher>) teacherList;

        return new PageResult4(t.getTotal(), t.getResult());
    }

    @Override
    public void deleteTeacher(String staffId) {
        adminMapper.deleteTeacher(staffId);
    }

    @Override
    public void updateTeacher(String staffId, Teacher teacher) {
        String deptName = teacher.getDeptName();
        String deptId = adminMapper.selectDeptIdByName(deptName);
        teacher.setDeptId(deptId);

        adminMapper.updateTeacher(staffId, teacher);
    }

    @Override
    public void resetTeacherPassword(String staffId) {
        teacherMapper.updatePassword(staffId, "123456");
    }

    @Override
    public void addTeacher(Teacher teacher) {
        String deptName = teacher.getDeptName();
        String deptId = adminMapper.selectDeptIdByName(deptName);
        teacher.setDeptId(deptId);

        adminMapper.addTeacher(teacher);
    }
}
