package com.example.StudentCourse.service;

import com.example.StudentCourse.pojo.Classes;
import com.example.StudentCourse.pojo.CourseSelection;
import com.example.StudentCourse.pojo.Teacher;
import com.example.StudentCourse.pojo.scoreDistribution;

import java.util.List;

public interface TeacherService {

    public Teacher login(Teacher teacher);

    List<Classes> showTeachClass(String teacherId, String semester, String courseId);

    List<CourseSelection> showStuentScore(String teacherId, String semester, String courseId, String classId);

    void updateScores(List<CourseSelection> studentScoreList, String teacherId);


    List<scoreDistribution> ScoreDistribution(String teacherId, String semester, String courseId, String classId);

    List<scoreDistribution> NormalScoreDistribution(String teacherId, String semester, String courseId, String classId);

    List<scoreDistribution> TestScoreDistribution(String teacherId, String semester, String courseId, String classId);
}
