package com.example.StudentCourse.service;

import com.example.StudentCourse.pojo.Classes;
import com.example.StudentCourse.pojo.CourseSelection;
import com.example.StudentCourse.pojo.Teacher;

import java.util.List;

public interface TeacherService {

    public Teacher login(Teacher teacher);

    List<Classes> showTeachClass(String teacherId, String semester, String courseId);

    List<CourseSelection> showStuentScore(String teacherId, String semester, String courseId, String classId);
}
