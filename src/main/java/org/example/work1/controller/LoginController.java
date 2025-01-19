package org.example.work1.controller;

import org.example.work1.entity.Teacher;
import org.example.work1.mapper.TeacherMapper;
import org.example.work1.model.LoginRequest;
import org.example.work1.model.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private TeacherMapper teacherMapper;

    // 用于保存日志信息
    private static List<String> logMessages = new ArrayList<>();

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        String logMessage = "收到登录请求: 用户名=" + loginRequest.getUsername() + ", 密码=" + loginRequest.getPassword();
        logMessages.add(logMessage);
        System.out.println(logMessage);

        // 测试数据库连接
        int count = teacherMapper.count();
        logMessage = "数据库中总记录数: " + count;
        logMessages.add(logMessage);
        System.out.println(logMessage);

        // 打印SQL查询参数
        logMessage = "执行SQL查询: SELECT * FROM teacher WHERE username='" + loginRequest.getUsername() +
                "' AND password='" + loginRequest.getPassword() + "'";
        logMessages.add(logMessage);
        System.out.println(logMessage);

        Teacher teacher = teacherMapper.findByUsernameAndPassword(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );

        logMessage = "数据库查询结果: " + (teacher != null ? "找到用户" : "未找到用户");
        logMessages.add(logMessage);
        System.out.println(logMessage);

        LoginResponse response = new LoginResponse();
        if (teacher != null) {
            logMessage = "登录成功";
            logMessages.add(logMessage);
            System.out.println(logMessage);
            response.setSuccess(true);
            response.setMessage("登录成功");
        } else {
            logMessage = "登录失败";
            logMessages.add(logMessage);
            System.out.println(logMessage);
            response.setSuccess(false);
            response.setMessage("用户名或密码错误");
        }

        return response;
    }

    // 新的接口，用于获取日志信息
    @GetMapping("/getLogs")
    public List<String> getLogs() {
        return logMessages;
    }
}