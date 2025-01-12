package com.example.StudentCourse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    private String staffId;
    private String semester;
    private String courseId;
    private String classId;
    private String studentIds;
    private String header;
    private String content;
    private Date sendTime;

}
