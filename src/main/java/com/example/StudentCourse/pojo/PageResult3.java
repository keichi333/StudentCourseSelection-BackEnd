package com.example.StudentCourse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult3 {
    private long total;
    private List<CourseSelection> studentCourseList;
}
