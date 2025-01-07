package com.example.StudentCourse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class scoreDistribution {
    private int minScore;
    private int maxScore;
    private int numberOfStudents;
}
