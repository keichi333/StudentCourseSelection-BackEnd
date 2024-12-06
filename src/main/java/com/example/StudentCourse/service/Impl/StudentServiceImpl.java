package com.example.StudentCourse.service.Impl;

import com.example.StudentCourse.mapper.StudentMapper;
import com.example.StudentCourse.pojo.Classes;
import com.example.StudentCourse.pojo.CourseSelection;
import com.example.StudentCourse.pojo.Student;
import com.example.StudentCourse.service.StudentService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Override
    public Boolean chooseClass(String studentId, Classes course) {
        // 1、获取该学生在指定学期已选的课程列表
        List<CourseSelection> courseList = studentMapper.getByStudentIdandSemester(studentId, course.getSemester());

        // 2、判断是否有已选课程或者时间重叠的课程
        // 定义星期的映射关系
        Map<String, Integer> weekMap = new HashMap<>();
        weekMap.put("星期一", 1);
        weekMap.put("星期二", 2);
        weekMap.put("星期三", 3);
        weekMap.put("星期四", 4);
        weekMap.put("星期五", 5);
        weekMap.put("星期六", 6);
        weekMap.put("星期天", 7);
        // 获取新课程的 class_time
        String newClassTime = course.getClassTime();
        String[] newParts = newClassTime.split("\\d+[-]\\d+");  // 分割出星期几部分
        String newDay = newParts[0];  // 例如 "星期三"
        String newRange = newClassTime.split(newDay)[1];    // 按照newDay(星期三)对newClassTime(星期三1-4)进行分割，得到时间段(1-4)
        // 获取时间范围
        String[] newRangeParts = newRange.split("-"); // 按照“-”对时间(1-4)进行分割，得到[1,4]
        int newStart = Integer.parseInt(newRangeParts[0]);
        int newEnd = Integer.parseInt(newRangeParts[1]);
        // 遍历已选课程列表
        for (CourseSelection selectedCourse : courseList) {
            // 获取已有课程的 class_time
            String existingClassTime = selectedCourse.getClassTime();
            String[] existingParts = existingClassTime.split("\\d+[-]\\d+");  // 分割出星期几部分
            String existingDay = existingParts[0];  // 例如 "星期三"
            String existingRange = existingClassTime.split(existingDay)[1];  // 获取时间范围部分 例如 "1-4"
            // 获取已有课程的时间范围
            String[] existingRangeParts = existingRange.split("-");
            int existingStart = Integer.parseInt(existingRangeParts[0]);
            int existingEnd = Integer.parseInt(existingRangeParts[1]);
            // 检查是否有相同的 course_id
            if (selectedCourse.getCourseId().equals(course.getCourseId())) {
                // 如果已选课程的 course_id 与传入的 course_id 相同，返回 false
                return false;
            }
            // 检查是否在同一天
            if (existingDay.equals(newDay)) {
                // 如果时间段有重叠，返回 false
                if (newStart <= existingEnd && newEnd >= existingStart) {
                    return false;
                }
            }
        }

        // 3、将所选择的课加入到选课表中
        String semester = course.getSemester();
        String courseId = course.getCourseId();
        String staffId = course.getStaffId();
        studentMapper.choose(studentId, semester, courseId, staffId);
        return true;
    }

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
