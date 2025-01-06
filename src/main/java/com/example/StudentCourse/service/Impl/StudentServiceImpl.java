package com.example.StudentCourse.service.Impl;

import com.example.StudentCourse.mapper.StudentMapper;
import com.example.StudentCourse.pojo.Classes;
import com.example.StudentCourse.pojo.CourseSelection;
import com.example.StudentCourse.pojo.Student;
import com.example.StudentCourse.service.StudentService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    // 学生登录
    @Override
    public Student login(Student stu) {
        return studentMapper.getByStudentIdandPassword(stu);
    }

    // 分页查询课程数据，支持搜索条件
    @Override
    public List<Classes> showClass(int page, int size, String semester, String courseId, String courseName,
                                   String staffId, String name, String classTime) {
        // 使用 PageHelper 插件进行分页
        PageHelper.startPage(page, size);

        // 调用 Mapper 查询方法，传入搜索条件
        return studentMapper.showClassWithFilters(semester, courseId, courseName, staffId, name, classTime);
    }

    // 查询学生的选课情况
    @Override
    public List<CourseSelection> showSelection(String studentId, String semester) {
        return studentMapper.showSelection(studentId,semester);
    }

    // 选课
    @Transactional
    @Override
    public Integer chooseClass(String studentId, Classes course) {
        // 1、获取该学生在指定学期已选的课程列表
        List<CourseSelection> courseList = studentMapper.getByStudentIdandSemester(studentId, course.getSemester(), course.getClassId());

        // 2、判断是否有已选课程或者时间重叠的课程

        // 解析新课程的上课时间
        String newClassTime = course.getClassTime();
        String[] newClassTimeParts = newClassTime.split("，");  // 分割出每一天的时间部分
        for (String newClassTimePart : newClassTimeParts) {
            String[] newParts = newClassTimePart.split("\\d+[-]\\d+");  // 分割出星期几部分
            String newDay = newParts[0].trim();  // 例如 "星期三"
            String newRange = newClassTimePart.split(newDay)[1].trim();  // 获取时间范围部分 例如 "1-4"
            // 获取时间范围
            String[] newRangeParts = newRange.split("-");
            int newStart = Integer.parseInt(newRangeParts[0]);
            int newEnd = Integer.parseInt(newRangeParts[1]);

            // 遍历已选课程列表
            for (CourseSelection selectedCourse : courseList) {
                // 解析已有课程的上课时间
                String existingClassTime = selectedCourse.getClassTime();
                String[] existingClassTimeParts = existingClassTime.split("，");  // 分割出每一天的时间部分
                for (String existingClassTimePart : existingClassTimeParts) {
                    String[] existingParts = existingClassTimePart.split("\\d+[-]\\d+");  // 分割出星期几部分
                    String existingDay = existingParts[0].trim();  // 例如 "星期三"
                    String existingRange = existingClassTimePart.split(existingDay)[1].trim();  // 获取时间范围部分 例如 "1-4"
                    // 获取已有课程的时间范围
                    String[] existingRangeParts = existingRange.split("-");
                    int existingStart = Integer.parseInt(existingRangeParts[0]);
                    int existingEnd = Integer.parseInt(existingRangeParts[1]);

                    // 检查是否有相同的 course_id
                    if (selectedCourse.getCourseId().equals(course.getCourseId())) {
                        // 如果已选课程的 course_id 与传入的 course_id 相同，返回 false
                        return 1;
                    }

                    // 检查是否在同一天
                    if (existingDay.equals(newDay)) {
                        // 如果时间段有重叠，返回 false
                        if (newStart <= existingEnd && newEnd >= existingStart) {
                            return 2;
                        }
                    }
                }
            }
        }

        // 3、将所选择的课加入到选课表中
        String semester = course.getSemester();
        String courseId = course.getCourseId();
        String staffId = course.getStaffId();
        String classTime = course.getClassTime();
        studentMapper.choose(studentId, semester, courseId, staffId, classTime);
        return 3;
    }

    // 删除已选课程
    @Override
    public void deleteClass(String studentId, CourseSelection course) {
        String courseId = course.getCourseId();
        String semester = course.getSemester();
        studentMapper.deleteClass(studentId,courseId, semester);
    }

    // 显示个人信息
    @Override
    public Student showInfo(String studentId) {
        return studentMapper.selectInfo(studentId);
    }

    @Override
    public void updateinfo(Student stu) {
        studentMapper.updateInfo(stu);
    }

    @Override
    public boolean isPasswordEqual(String studentId, String currentPassword) {
        String id = studentMapper.findPassword(studentId,currentPassword);
        // 如果没有找到学生记录或者密码不匹配，返回 false
        return id != null;
    }

    @Override
    public void updatePassword(String studentId, String newPassword) {
        studentMapper.updatePassword(studentId,newPassword);
    }


}
