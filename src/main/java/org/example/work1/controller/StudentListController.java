package org.example.work1.controller;

import org.example.work1.entity.Student;
import org.example.work1.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentListController {
    @Autowired
    private StudentMapper studentMapper;

    @GetMapping("/list")
    @ResponseBody
    public List<Student> getAllStudents() {
        return studentMapper.getAllStudents();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String deleteStudent(@PathVariable Long id) {
        int result = studentMapper.deleteStudent(id);
        return result > 0 ? "success" : "fail";
    }

    @PostMapping("/add")
    @ResponseBody
    public String addStudent(@ModelAttribute Student student) {
        student.setCreateTime(LocalDateTime.now());
        student.setStatus(1); // 默认状态为在读
        student.setRoleId(2); // 默认角色ID为2（学生）
        int result = studentMapper.addStudent(student);
        return result > 0 ? "success" : "fail";
    }
}