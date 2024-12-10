package com.example.StudentCourse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    private String staffId;
    private String name;
    private String password;
    private String sex;
    private Date dateOfBirth;
    private String professionalRanks;
    private BigDecimal salary;
    private String deptId;
    private String deptName;

}
