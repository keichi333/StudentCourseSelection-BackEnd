package com.example.StudentCourse.service;

import com.example.StudentCourse.pojo.*;

import java.util.List;

public interface TeacherService {

    public Teacher login(Teacher teacher);

    List<Classes> showTeachClass(String teacherId, String semester, String courseId);

    List<CourseSelection> showStuentScore(String teacherId, String semester, String courseId, String classId);

    void updateScores(List<CourseSelection> studentScoreList, Proportion proportion, String teacherId);


    List<scoreDistribution> ScoreDistribution(String teacherId, String semester, String courseId, String classId);

    List<scoreDistribution> NormalScoreDistribution(String teacherId, String semester, String courseId, String classId);

    List<scoreDistribution> TestScoreDistribution(String teacherId, String semester, String courseId, String classId);

    Teacher showInfo(String teacherId);

    boolean isPasswordEqual(String teacherId, String currentPassword);

    void updatePassword(String teacherId, String newPassword);
}
