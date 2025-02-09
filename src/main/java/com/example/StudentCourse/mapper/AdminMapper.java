package com.example.StudentCourse.mapper;

import com.example.StudentCourse.pojo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {

    // 管理员登录
    @Select("select * from admin where admin_id=#{adminId} and password=#{password}")
    Admin getByAdminIdandPassword(Admin admin);

    // 管理员信息查询
    @Select("select * from admin where admin_id=#{adminId}")
    Student selectInfo(String adminId);

    // 管理员修改密码
    @Select("select admin_id from admin where admin_id=#{adminId} and password=#{currentPassword}")
    String findPassword(String adminId, String currentPassword);
    @Update("update admin set password=#{newPassword} where admin_id=#{adminId}")
    void updatePassword(String adminId, String newPassword);

    // 获取当前学期
    @Select("select semester from semester where id='1'")
    String getCurrentSemester();

    // 修改当前学期
    @Update("update semester set semester=#{semester} where id='1'")
    void updateSemester(String semester);

    List<Student> showStudentWithFilters(String studentId, String name, String sex, String dateOfBirth, String nativePlace, String mobilePhone, String deptName);

    // 学生信息修改
    @Update("update student set name=#{student.name}, sex=#{student.sex}, date_of_birth=#{student.dateOfBirth}, native_place=#{student.nativePlace}, mobile_phone=#{student.mobilePhone}, dept_id=#{student.deptId} where student_id=#{student.studentId}")
    void updateStudent(String studentId, Student student);

    @Select("select dept_id from department where dept_name=#{deptName}")
    String selectDeptIdByName(String deptName);

    // 学院信息查询
    @Select("select dept_name from department")
    List<String> showDeptList();

    // 删除学生
    @Select("CALL DeleteStudentWithSelection(#{studentId})")
    void deleteStudent(String studentId);

    // 查询选课信息
    List<CourseSelection> showCourseSelectionWithFilters(String semester, String studentId, String name, String courseId, String courseName, String classId, String staffId, String staffName, String credit, String classTime);

    // 删除选课信息
    @Delete("delete from course_selection where student_id=#{studentId} and course_id=#{courseId} and class_id=#{classId} and semester=#{semester}")
    void deleteClass(String studentId, String courseId, String classId, String semester);

    // 查询教师信息
    List<Teacher> showTeacherWithFilters(String staffId, String name, String sex, String dateOfBirth, String professionalRanks, String salary, String deptName);

    // 删除教师信息
    @Select("CALL DeleteTeacherWithSelection(#{staffId})")
    void deleteTeacher(String staffId);

    // 修改教师信息
    @Update("update teacher set name=#{teacher.name}, sex=#{teacher.sex}, date_of_birth=#{teacher.dateOfBirth}, professional_ranks=#{teacher.professionalRanks}, salary=#{teacher.salary}, dept_id=#{teacher.deptId} where staff_id=#{teacher.staffId}")
    void updateTeacher(String staffId, Teacher teacher);

    // 新增教师
    @Insert("insert into teacher(staff_id, name, password, sex, date_of_birth, professional_ranks, salary, dept_id) values(#{staffId}, #{name}, '123456', #{sex}, #{dateOfBirth}, #{professionalRanks}, #{salary}, #{deptId})")
    void addTeacher(Teacher teacher);

    // 查询教师课程
    List<Classes> showTeachClass(String semester, String staffId, String name, String courseId, String courseName, String classId, String classTime);

    // 删除教师的课程安排
    @Select("CALL DeleteTeacherClassWithSelection(#{staffId}, #{courseId}, #{classId}, #{semester})")
    void deleteTeacherClass(String staffId, String courseId, String classId, String semester);

    // 查询所有课程
    List<Course> getAllCourses(String courseId);

    // 查询教师课程
    List<Classes> getTeacherCourse(String staffId, String semester);

    // 新增教师课程
    void addTeacherCourse(String staffId, String semester, String courseId, String classId, String classTime, String maxStudents);

    // 获取最大班级号
    @Select("SELECT MAX(class_id) AS max_class_id FROM class WHERE semester = #{semester} AND course_id = #{courseId}")
    String getMaxClassId(String semester, String courseId);
}
