package org.example.work1.controller;

import org.example.work1.entity.Admin;
import org.example.work1.entity.Teacher;
import org.example.work1.mapper.TeacherxiugaiMapper;
import org.example.work1.model.LoginRequest;
import org.example.work1.model.LoginResponse;
import org.example.work1.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private TeacherxiugaiMapper teacherMapper;
    
    @Autowired
    private AdminRepository adminRepository;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = new LoginResponse();
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // 1. 检查管理员表
        Admin admin = adminRepository.findByUsernameAndUserpass(username, password);
        if (admin != null) {
            response.setSuccess(true);
            response.setMessage("登录成功");
            response.setRoleId(2);  // 管理员
            response.setUsername(admin.getUsername());
            response.setNickname(admin.getNickname());
            response.setSex(admin.getSex());
            response.setPhone(admin.getPhone());
            response.setEmail(admin.getEmail());
            return response;
        }

        // 2. 检查教师表
        Teacher teacher = teacherMapper.findByUsernameAndPassword(username, password);
        if (teacher != null) {
            response.setSuccess(true);
            response.setMessage("登录成功");
            response.setRoleId(1);  // 教师
            response.setUsername(teacher.getUsername());
            String sexText = String.valueOf(teacher.getSex());
            response.setSex(sexText);
            response.setPhone(teacher.getPhone());
            response.setEmail(null);  // 教师没有邮箱字段
            return response;
        }

        // 登录失败
        response.setSuccess(false);
        response.setMessage("用户名或密码错误");
        return response;
    }
} 