package com.example.StudentCourse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateScoresRequest {
    private List<CourseSelection> studentList; // 学生成绩列表
    private Proportion proportion;             // 成绩比例
}
