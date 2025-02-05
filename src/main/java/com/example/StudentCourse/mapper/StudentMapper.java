package com.example.StudentCourse.mapper;

import com.example.StudentCourse.pojo.Classes;
import com.example.StudentCourse.pojo.CourseSelection;
import com.example.StudentCourse.pojo.Email;
import com.example.StudentCourse.pojo.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {

    // 根据学生id和密码获取学生信息
    @Select("select * from student where student_id=#{studentId} and password=#{password}")
    public Student getByStudentIdandPassword(Student stu);

    // 根据查询条件显示课程信息
    List<Classes> showClassWithFilters(@Param("semester") String semester,
                                       @Param("courseId") String courseId,
                                       @Param("courseName") String courseName,
                                       @Param("staffId") String staffId,
                                       @Param("name") String name,
                                       @Param("classTime") String classTime);

    int getClassCount(String semester, String courseId, String courseName, String staffId, String name, String classTime);

    // 显示选课情况
    List<CourseSelection> showSelection(String studentId, String semester);

    // 根据学生id和学期查看对应选课表中的数据
    List<CourseSelection> getByStudentIdandSemester(String studentId, String semester);

    // 选课(向选课表中添加数据)
    void choose(String studentId, String semester, String courseId, String staffId, String classTime);

    // 退课
    @Delete("delete from course_selection where student_id=#{studentId} and semester=#{semester} and course_id =#{courseId}")
    void deleteClass(String studentId, String courseId, String semester);

    // 显示个人信息
    Student selectInfo(String studentId);

    // 修改个人信息
    @Update("update student set date_of_birth=#{dateOfBirth}, native_place=#{nativePlace}, mobile_phone=#{mobilePhone} where student_id=#{studentId}")
    void updateInfo(Student stu);

    // 查询密码
    @Select("select student_id from student where student_id=#{studentId} and password=#{currentPassword}")
    String findPassword(String studentId, String currentPassword);

    // 修改密码
    @Update("update student set password=#{newPassword} where student_id=#{studentId}")
    void updatePassword(String studentId, String newPassword);

    // 查看通知列表
    List<Email> showEmailList(String studentId);

    // 获取当前学期
    @Select("select semester from semester where id='1'")
    String getCurrentSemester();

}
