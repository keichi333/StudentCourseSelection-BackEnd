package com.example.StudentCourse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Classes {
    private String semester;
    private String courseId;
    private String courseName;
    private String staffId;
    private String name;
    private String classTime;
}
