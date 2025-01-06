package com.example.StudentCourse.service.Impl;

import com.example.StudentCourse.mapper.TeacherMapper;
import com.example.StudentCourse.pojo.Classes;
import com.example.StudentCourse.pojo.CourseSelection;
import com.example.StudentCourse.pojo.Teacher;
import com.example.StudentCourse.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Teacher login(Teacher teacher){
        return teacherMapper.getByStaffIdandPassword(teacher);
    }

    @Override
    public List<Classes> showTeachClass(String teacherId, String semester, String courseId) {
        return teacherMapper.showTeachClass(teacherId, semester, courseId);
    }

    @Override
    public List<CourseSelection> showStuentScore(String teacherId, String semester, String courseId, String classId) {
        return teacherMapper.showStuentScoreList(teacherId, semester, courseId, classId);
    }

    @Override
    public void updateScores(List<CourseSelection> studentScoreList, String teacherId) {
        String courseId;
        String semester;
        Integer normalScore;
        Integer testScore;
        Integer totalScore;
        String studentId;
        // 对studentScoreList中的每一行，进行更新
        for (CourseSelection courseSelection : studentScoreList) {
            courseId = courseSelection.getCourseId();
            semester = courseSelection.getSemester();
            normalScore = courseSelection.getNormalScore();
            testScore = courseSelection.getTestScore();
            totalScore = courseSelection.getTotalScore();
            studentId = courseSelection.getStudentId();
            teacherMapper.updateScore(courseId, semester, teacherId, studentId, normalScore, testScore, totalScore);
        }
    }
}
