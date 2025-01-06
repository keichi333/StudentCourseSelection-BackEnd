package com.example.StudentCourse.mapper;

import com.example.StudentCourse.pojo.Classes;
import com.example.StudentCourse.pojo.CourseSelection;
import com.example.StudentCourse.pojo.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeacherMapper {

    @Select("select * from teacher where staff_id=#{staffId} and password=#{password}")
    public Teacher getByStaffIdandPassword(Teacher teacher);

    public List<Classes> showTeachClass(String teacherId, String semester, String courseId);


    List<CourseSelection> showStuentScoreList(String teacherId, String semester, String courseId, String classId);
}
