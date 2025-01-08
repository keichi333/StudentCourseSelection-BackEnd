package com.example.StudentCourse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Proportion {
    private Integer normalProportion; // 平时成绩占比
    private Integer testProportion;   // 考试成绩占比
}
