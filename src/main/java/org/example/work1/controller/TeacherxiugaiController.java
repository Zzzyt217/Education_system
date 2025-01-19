package org.example.work1.controller;

import org.example.work1.entity.Teacher;
import org.example.work1.mapper.TeacherxiugaiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher")
public class TeacherxiugaiController {

    @Autowired
    private TeacherxiugaiMapper teacherMapper;

    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestParam String username,
                                          @RequestParam String oldPassword,
                                          @RequestParam String newPassword) {
        try {
            // 验证原密码是否正确
            Teacher teacher = teacherMapper.findByUsernameAndPassword(username, oldPassword);
            if (teacher == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("原密码错误");
            }
            
            // 更新密码
            teacher.setPassword(newPassword);
            // 使用teacherMapper更新密码
            int result = teacherMapper.updateTeacherPassword(username, newPassword);
            
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