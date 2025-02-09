package com.example.StudentCourse.service.Impl;

import com.example.StudentCourse.mapper.AdminMapper;
import com.example.StudentCourse.mapper.StudentMapper;
import com.example.StudentCourse.mapper.TeacherMapper;
import com.example.StudentCourse.pojo.*;
import com.example.StudentCourse.service.AdminService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Admin login(Admin admin) {
        return adminMapper.getByAdminIdandPassword(admin);
    }

    @Override
    public Student showInfo(String adminId) {
        return adminMapper.selectInfo(adminId);
    }

    @Override
    public boolean isPasswordEqual(String adminId, String currentPassword) {
        String id = adminMapper.findPassword(adminId,currentPassword);
        // 如果没有找到学生记录或者密码不匹配，返回 false
        return id != null;
    }

    @Override
    public void updatePassword(String adminId, String newPassword) {
        adminMapper.updatePassword(adminId,newPassword);
    }

    @Override
    public String getCurrentSemester() {
        return adminMapper.getCurrentSemester();
    }

    // 修改学期
    @Override
    public void updateSemester(String semester) {
        adminMapper.updateSemester(semester);
    }

    @Override
    public PageResult2 showStudentList(int page, int size, String studentId, String name, String sex, String dateOfBirth, String nativePlace, String mobilePhone, String deptName) {
        // 使用 PageHelper 插件进行分页
        PageHelper.startPage(page, size);

        // 调用 Mapper 查询方法，传入搜索条件
        List<Student> studentList = adminMapper.showStudentWithFilters(studentId, name, sex, dateOfBirth, nativePlace, mobilePhone, deptName);
        Page<Student> p = (Page<Student>) studentList;

        return new PageResult2(p.getTotal(), p.getResult());
    }

    @Override
    public void updateStudent(String studentId, Student student) {
        String deptName = student.getDeptName();
        String deptId = adminMapper.selectDeptIdByName(deptName);
        student.setDeptId(deptId);

        adminMapper.updateStudent(studentId, student);
    }

    @Override
    public List<String> showDeptList() {
        return adminMapper.showDeptList();
    }

    @Override
    public void deleteStudent(String studentId) {
        adminMapper.deleteStudent(studentId);
    }

    @Override
    public void resetPassword(String studentId) {
        studentMapper.updatePassword(studentId, "123456");
    }

    @Override
    public void addStudent(Student student) {
        String deptName = student.getDeptName();
        String deptId = adminMapper.selectDeptIdByName(deptName);
        student.setDeptId(deptId);

        studentMapper.addStudent(student);
    }

    @Override
    public PageResult3 showStudentCourseList(int page, int size, String semester, String studentId, String name, String courseId, String courseName, String classId, String staffId, String staffName, String credit, String classTime) {
        // 使用 PageHelper 插件进行分页
        PageHelper.startPage(page, size);

        // 调用 Mapper 查询方法，传入搜索条件
        List<CourseSelection> studentCourseList = adminMapper.showCourseSelectionWithFilters(semester, studentId, name, courseId, courseName, classId, staffId, staffName, credit, classTime);
        Page<CourseSelection> c = (Page<CourseSelection>) studentCourseList;

        return new PageResult3(c.getTotal(), c.getResult());
    }

    @Override
    public void deleteClass(String studentId, String courseId, String classId, String semester) {
        adminMapper.deleteClass(studentId, courseId, classId, semester);
    }

    @Override
    public List<Classes> showCourseList(String semester, String courseId) {
        return studentMapper.showClassWithFilters(semester, courseId, null, null, null, null);
    }

    @Override
    public PageResult4 showTeacherList(int page, int size, String staffId, String name, String sex, String dateOfBirth, String professionalRanks, String salary, String deptName) {
        // 使用 PageHelper 插件进行分页
        PageHelper.startPage(page, size);

        // 调用 Mapper 查询方法，传入搜索条件
        List<Teacher> teacherList = adminMapper.showTeacherWithFilters(staffId, name, sex, dateOfBirth, professionalRanks, salary, deptName);
        Page<Teacher> t = (Page<Teacher>) teacherList;

        return new PageResult4(t.getTotal(), t.getResult());
    }

    @Override
    public void deleteTeacher(String staffId) {
        adminMapper.deleteTeacher(staffId);
    }

    @Override
    public void updateTeacher(String staffId, Teacher teacher) {
        String deptName = teacher.getDeptName();
        String deptId = adminMapper.selectDeptIdByName(deptName);
        teacher.setDeptId(deptId);

        adminMapper.updateTeacher(staffId, teacher);
    }

    @Override
    public void resetTeacherPassword(String staffId) {
        teacherMapper.updatePassword(staffId, "123456");
    }

    @Override
    public void addTeacher(Teacher teacher) {
        String deptName = teacher.getDeptName();
        String deptId = adminMapper.selectDeptIdByName(deptName);
        teacher.setDeptId(deptId);

        adminMapper.addTeacher(teacher);
    }

    @Override
    public PageResult5 showTeacherCourseList(int page, int size, String semester, String staffId, String name, String courseId, String courseName, String classId, String classTime) {
        // 使用 PageHelper 插件进行分页
        PageHelper.startPage(page, size);

        // 调用 Mapper 查询方法，传入搜索条件
        List<Classes> teacherCourseList = adminMapper.showTeachClass(semester, staffId, name, courseId, courseName, classId, classTime);
        Page<Classes> tc = (Page<Classes>) teacherCourseList;

        return new PageResult5(tc.getTotal(), tc.getResult());
    }

    @Override
    public void deleteTeacherClass(String staffId, String courseId, String classId, String semester) {
        adminMapper.deleteTeacherClass(staffId, courseId, classId, semester);
    }

    @Override
    public List<Course> getAllCourses(String courseId) {
        return adminMapper.getAllCourses(courseId);
    }

    @Override
    public List<Classes> getTeacherCourse(String staffId, String semester) {
        return adminMapper.getTeacherCourse(staffId, semester);
    }

    @Override
    @Transactional
    public Integer chooseClass(String staffId, String semester, String courseId, String classTime, String maxStudents) {
        // 1、获取该教师在指定学期已选的课程列表
        List<Classes> courseList = adminMapper.getTeacherCourse(staffId, semester);
        log.info("已安排课程列表：{}", courseList);

        // 2、判断是否有已选课程或者时间重叠的课程
        // 解析新课程的上课时间
        String[] newClassTimeParts = classTime.split("，");  // 分割出每一天的时间部分
        for (String newClassTimePart : newClassTimeParts) {
            // 分割出星期几和时间范围
            String[] newParts = newClassTimePart.split("\\d+[-]\\d+");  // 分割出星期几部分
            String newDay = newParts[0].trim();  // 例如 "星期三"
            String newRange = newClassTimePart.split(newDay)[1].trim();  // 获取时间范围部分 例如 "1-4"
            // 获取时间范围
            String[] newRangeParts = newRange.split("-");
            int newStart = Integer.parseInt(newRangeParts[0]);
            int newEnd = Integer.parseInt(newRangeParts[1]);

            // 遍历已选课程列表
            for (Classes selectedCourse : courseList) {
                // 解析已有课程的上课时间
                String existingClassTime = selectedCourse.getClassTime();
                String[] existingClassTimeParts = existingClassTime.split("，");  // 分割出每一天的时间部分
                for (String existingClassTimePart : existingClassTimeParts) {
                    // 分割出星期几和时间范围
                    String[] existingParts = existingClassTimePart.split("\\d+[-]\\d+");  // 分割出星期几部分
                    String existingDay = existingParts[0].trim();  // 例如 "星期三"
                    String existingRange = existingClassTimePart.split(existingDay)[1].trim();  // 获取时间范围部分 例如 "1-4"
                    // 获取已有课程的时间范围
                    String[] existingRangeParts = existingRange.split("-");
                    int existingStart = Integer.parseInt(existingRangeParts[0]);
                    int existingEnd = Integer.parseInt(existingRangeParts[1]);

                    // 检查是否在同一天
                    if (existingDay.equals(newDay)) {
                        // 如果时间段有重叠，返回 2
                        if (newStart <= existingEnd && newEnd >= existingStart) {
                            return 2;
                        }
                    }
                }
            }
        }

        // 获取到最大的 classId
        String GetClassId = adminMapper.getMaxClassId(semester, courseId);

        // 将字符串转换为整数并递增
        int nextClassId = Integer.parseInt(GetClassId) + 1;

        // 将整数转换回字符串并格式化为两位数
        String classId = String.format("%02d", nextClassId);

        // 3、将所选择的课加入到选课表中
        adminMapper.addTeacherCourse(staffId, semester, courseId, classId, classTime, maxStudents);
        return 3; // 成功
    }
}
