package org.example.work1.controller;

import org.example.work1.entity.Teacher;
import org.example.work1.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    @Autowired
    private TeacherMapper teacherMapper;

    @GetMapping("/all")
    public List<Teacher> getAllTeachers() {
        return teacherMapper.findAllTeachers();
    }

    @GetMapping("/search")
    public List<Teacher> searchTeachers(@RequestParam String keyword) {
        return teacherMapper.searchTeachers(keyword);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Long id) {
        try {
            int result = teacherMapper.deleteTeacher(id);
            if (result > 0) {
                return ResponseEntity.ok().body("success");
            } else {
                return ResponseEntity.badRequest().body("failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("删除失败：" + e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addTeacher(@RequestBody Teacher teacher) {
        try {
            // 设置默认角色ID为1（教师角色）
            teacher.setRoleId(1L);
            int result = teacherMapper.insertTeacher(teacher);
            if (result > 0) {
                return ResponseEntity.ok("success");
            } else {
                return ResponseEntity.badRequest().body("failed");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateTeacher(@RequestBody Teacher teacher) {
        try {
            System.out.println("接收到的教师数据: " + teacher); // 添加日志
            int result = teacherMapper.updateTeacher(teacher);
            if (result > 0) {
                return ResponseEntity.ok("success");
            } else {
                return ResponseEntity.badRequest().body("failed");
            }
        } catch (Exception e) {
            e.printStackTrace(); // 添加错误日志
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Teacher getTeacherById(@PathVariable Long id) {
        return teacherMapper.findById(id);
    }
}