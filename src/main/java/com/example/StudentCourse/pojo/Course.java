package com.example.StudentCourse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
     private String courseId;
     private String courseName;
     private Integer credit;
     private Integer creditHours;
     private String deptId;
     private String deptName;
}
