package com.example.StudentCourse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String studentId;
    private String name;
    private String password;
    private String sex;
    private Date dateOfBirth;
    private String nativePlace;
    private String mobilePhone;
    private String deptId;
    private String deptName;
}
