package com.example.StudentCourse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.InterfaceAddress;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSelection {
    String studentId;
    String semester;
    String courseId;
    String courseName;
    String staffId;
    String staffName;
    Integer credit;
    String classTime;
    Integer normalScore;
    Integer testScore;
    Integer totalScore;
}
