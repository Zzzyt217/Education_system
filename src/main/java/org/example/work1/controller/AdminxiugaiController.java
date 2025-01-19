package org.example.work1.controller;

import org.example.work1.entity.Admin;
import org.example.work1.mapper.AdminxiugaiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminxiugaiController {

    @Autowired
    private AdminxiugaiMapper adminMapper;

    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestParam String username,
                                          @RequestParam String oldPassword,
                                          @RequestParam String newPassword) {
        try {
            // 验证原密码是否正确
            Admin admin = adminMapper.findByUsernameAndPassword(username, oldPassword);
            if (admin == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("原密码错误");
            }
            
            // 更新密码
            admin.setUserpass(newPassword);
            // 使用adminMapper更新密码
            int result = adminMapper.updateAdminPassword(username, newPassword);
            
            if (result > 0) {
                return ResponseEntity.ok("密码修改成功");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("密码修改失败");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("密码修改失败：" + e.getMessage());
        }
    }
} 