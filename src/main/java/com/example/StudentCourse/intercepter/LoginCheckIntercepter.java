package com.example.StudentCourse.intercepter;

import com.alibaba.fastjson.JSONObject;
import com.example.StudentCourse.pojo.Result;
import com.example.StudentCourse.utils.JwtUtils;
import com.example.StudentCourse.utils.UserContext;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoginCheckIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {

        // 设置 CORS 响应头，允许跨域请求
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:8080"); // 前端地址
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");

        // 如果是预检请求（OPTIONS），直接返回200
        if ("OPTIONS".equals(req.getMethod())) {
            resp.setStatus(HttpServletResponse.SC_OK);
            return false; // 返回false表示此请求已处理，不再传递给其他拦截器
        }

        // 获取请求url
        String url = req.getRequestURI().toString();
        log.info("请求的url:{}", url);

        // 判断请求url中是否包含login（登录相关接口）
        if (url.contains("login")) {
            log.info("登录操作，放行...");
            return true;
        }

        // 获取请求头中的 Authorization 字段
        String authorizationHeader = req.getHeader("Authorization");
        log.info("请求的 Authorization 头部: {}", authorizationHeader);

        if (!StringUtils.hasLength(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            log.info("Authorization 头为空或格式不正确，返回未登录的信息");
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);  // 写入未登录信息
            return false; // 拦截请求，未登录
        }

        // 提取 JWT 令牌（去除 "Bearer " 前缀）
        String jwt = authorizationHeader.substring(7);

        // 解析 token，如果解析失败，说明未登录
        try {
            JwtUtils.parseJWT(jwt);  // 解析 token
        } catch (Exception e) {
            e.printStackTrace();
            log.info("解析令牌失败，返回未登录错误信息");
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);  // 写入未登录信息
            return false; // 拦截请求，未登录
        }
        // 获取 token 中的用户信息
        Claims claims = JwtUtils.parseJWT(jwt);  // 解析JWT
        String userRole = claims.get("role", String.class);
        if(userRole.equals("teacher")){
            String Id = claims.get("Id", String.class);
            UserContext.setTeacher(Id);
            log.info("教师用户已登录，ID: {}", Id);
        }
        else if(userRole.equals("student")){
            String Id = claims.get("Id", String.class);
            UserContext.setUser(Id);
            log.info("学生用户已登录，ID: {}", Id);
        }
        else if(userRole.equals("admin")){
            String Id = claims.get("Id", String.class);
            UserContext.setUser(Id);
            log.info("管理员用户已登录，ID: {}", Id);
        }
        log.info("令牌合法，放行");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
       UserContext.clearContext(); // 清除用户信息
        log.info("学生信息已清除:{}", UserContext.getUser());
        log.info("教师信息已清除:{}", UserContext.getTeacher());
    }

}


