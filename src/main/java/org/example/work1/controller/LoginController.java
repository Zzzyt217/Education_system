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
        System.out.println("收到登录请求: 用户名=" + loginRequest.getUsername() + ", 密码=" + loginRequest.getPassword());
        
        LoginResponse response = new LoginResponse();
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // 1. 检查管理员表
        Admin admin = adminRepository.findByUsernameAndUserpass(username, password);
        if (admin != null) {
            System.out.println("管理员登录成功，用户信息：");
            System.out.println("用户名: " + admin.getUsername());
            System.out.println("昵称: " + admin.getNickname());
            System.out.println("性别: " + admin.getSex());
            System.out.println("电话: " + admin.getPhone());
            System.out.println("邮箱: " + admin.getEmail());

            response.setSuccess(true);
            response.setMessage("登录成功");
            response.setRoleId(2);  // 管理员
            response.setUsername(admin.getUsername());
            response.setNickname(admin.getNickname());
            response.setSex(admin.getSex());
            response.setPhone(admin.getPhone());
            response.setEmail(admin.getEmail());

            System.out.println("返回的响应对象：");
            System.out.println("success: " + response.isSuccess());
            System.out.println("roleId: " + response.getRoleId());
            System.out.println("username: " + response.getUsername());
            System.out.println("nickname: " + response.getNickname());
            System.out.println("sex: " + response.getSex());
            System.out.println("phone: " + response.getPhone());
            System.out.println("email: " + response.getEmail());
            
            return response;
        }

        // 2. 检查教师表
        Teacher teacher = teacherMapper.findByUsernameAndPassword(username, password);
        if (teacher != null) {
            System.out.println("教师登录成功，用户信息：");
            System.out.println("用户名: " + teacher.getUsername());
            System.out.println("性别代码: " + teacher.getSex());
            System.out.println("电话: " + teacher.getPhone());

            response.setSuccess(true);
            response.setMessage("登录成功");
            response.setRoleId(1);  // 教师
            response.setUsername(teacher.getUsername());
            
            // 将教师的性别数字转换为字符串
            String sexText = String.valueOf(teacher.getSex());  // 直接转换为字符串
            System.out.println("转换后的性别值: " + sexText);
            
            response.setSex(sexText);
            response.setPhone(teacher.getPhone());
            response.setEmail(null);  // 教师没有邮箱字段

            System.out.println("返回的响应对象：");
            System.out.println("success: " + response.isSuccess());
            System.out.println("roleId: " + response.getRoleId());
            System.out.println("username: " + response.getUsername());
            System.out.println("sex: " + response.getSex());
            System.out.println("phone: " + response.getPhone());
            System.out.println("email: " + response.getEmail());
            
            return response;
        }

        // 登录失败
        System.out.println("登录失败");
        response.setSuccess(false);
        response.setMessage("用户名或密码错误");
        return response;
    }
} 