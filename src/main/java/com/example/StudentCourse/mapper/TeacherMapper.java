package com.example.StudentCourse.mapper;

import com.example.StudentCourse.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TeacherMapper {

    @Select("select * from teacher where staff_id=#{staffId} and password=#{password}")
    public Teacher getByStaffIdandPassword(Teacher teacher);

    public List<Classes> showTeachClass(String teacherId, String semester, String courseId);


    List<CourseSelection> showStuentScoreList(String teacherId, String semester, String courseId, String classId);

    void updateScore(String courseId, String semester, String teacherId, String studentId, Integer normalScore, Integer testScore, Integer normalProportion, Integer testProportion);

    List<scoreDistribution> ScoreDistribution(String teacherId, String semester, String courseId, String classId);
    List<scoreDistribution> NormalScoreDistribution(String teacherId, String semester, String courseId, String classId);
    List<scoreDistribution> TestScoreDistribution(String teacherId, String semester, String courseId, String classId);

    // 查询教师个人信息
    Teacher showInfo(String teacherId);

    @Select("select staff_id from teacher where staff_id=#{teacherId} and password=#{currentPassword}")
    String findPassword(String teacherId, String currentPassword);

    @Update("update teacher set password=#{newPassword} where staff_id=#{teacherId}")
    void updatePassword(String teacherId, String newPassword);

    @Select("select student_ids, header, content, send_time from email where staff_id=#{teacherId} and semester=#{semester} and course_id=#{courseId} and class_id=#{classId}")
    List<Email> showEmailList(String teacherId, String semester, String courseId, String classId);

    @Update("insert into email(staff_id, semester, course_id, class_id, student_ids, header, content, send_time) values(#{email.staffId}, #{email.semester}, #{email.courseId}, #{email.classId}, #{email.studentIds}, #{email.header}, #{email.content}, #{email.sendTime})")
    void sendEmail(Email email, String teacherId);
}
