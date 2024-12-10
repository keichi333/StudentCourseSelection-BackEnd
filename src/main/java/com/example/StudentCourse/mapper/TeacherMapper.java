package com.example.StudentCourse.mapper;

import com.example.StudentCourse.pojo.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TeacherMapper {

    @Select("select * from teacher where staff_id=#{staffId} and password=#{password}")
    public Teacher getByStaffIdandPassword(Teacher teacher);
}
