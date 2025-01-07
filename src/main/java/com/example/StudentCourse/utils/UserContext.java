package com.example.StudentCourse.utils;

public class UserContext {
    // 用于保存用户ID的 ThreadLocal
    private static final ThreadLocal<String> userThreadLocal = new ThreadLocal<>();
    // 用于保存教师ID的 ThreadLocal
    private static final ThreadLocal<String> teacherThreadLocal = new ThreadLocal<>();

    /**
     * 保存当前登录用户信息到 ThreadLocal
     * @param userId 用户 ID
     */
    public static void setUser(String userId) {
        userThreadLocal.set(userId);
    }

    /**
     * 获取当前登录用户信息
     * @return 用户 ID
     */
    public static String getUser() {
        return userThreadLocal.get();
    }

    /**
     * 移除当前登录用户信息
     */
    public static void removeUser() {
        userThreadLocal.remove();
    }

    /**
     * 保存当前登录教师信息到 ThreadLocal
     * @param teacherId 教师 ID
     */
    public static void setTeacher(String teacherId) {
        teacherThreadLocal.set(teacherId);
    }

    /**
     * 获取当前登录教师信息
     * @return 教师 ID
     */
    public static String getTeacher() {
        return teacherThreadLocal.get();
    }

    /**
     * 移除当前登录教师信息
     */
    public static void removeTeacher() {
        teacherThreadLocal.remove();
    }

    /**
     * 清除所有上下文信息 (用户和教师信息)
     */
    public static void clearContext() {
        userThreadLocal.remove();
        teacherThreadLocal.remove();
    }
}
