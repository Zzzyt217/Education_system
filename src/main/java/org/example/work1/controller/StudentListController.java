package org.example.work1.controller;

import org.example.work1.entity.Student;
import org.example.work1.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/api/students")
public class StudentListController {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/list")
    @ResponseBody
    public List<Student> getAllStudents() {
        return studentMapper.getAllStudents();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public String deleteStudent(@PathVariable("id") Long id) {
        try {
            // 先获取要删除的学生信息
            Student student = studentMapper.getStudentById(id);
            if (student == null) {
                return "fail";
            }

            // 先删除选课表中的关联记录
            String username = student.getUsername();
            jdbcTemplate.update("DELETE FROM SC WHERE username = ?", username);

            // 然后删除学生
            int result = studentMapper.deleteStudent(id);
            return result > 0 ? "success" : "fail";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @PostMapping("/add")
    @ResponseBody
    public String addStudent(@RequestBody Student student) {
        try {
            System.out.println("接收到的学生数据: " + student); // 打印接收到的数据
            
            // 设置默认值
            student.setCreateTime(LocalDateTime.now());
            student.setStatus(1);
            student.setRoleId(0);
            
            System.out.println("处理后的学生数据: " + student); // 打印处理后的数据
            
            int result = studentMapper.addStudent(student);
            System.out.println("插入结果: " + result); // 打印插入结果
            
            return result > 0 ? "success" : "fail";
        } catch (Exception e) {
            System.err.println("添加学生时发生错误: " + e.getMessage()); // 打印错误信息
            e.printStackTrace();
            return "fail";
        }
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Student> searchStudents(
            @RequestParam(required = false) String keyword) {
        System.out.println("收到搜索请求，关键字: " + keyword); // 调试日志1
        List<Student> results = studentMapper.searchStudents(keyword);
        System.out.println("搜索结果数量: " + results.size()); // 调试日志2
        return results;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Student getStudent(@PathVariable("id") Long id) {
        return studentMapper.getStudentById(id);
    }

    @PostMapping("/update")
    @ResponseBody
    public String updateStudent(@RequestBody Student student) {
        try {
            System.out.println("接收到的修改数据: " + student); // 添加日志
            int result = studentMapper.updateStudent(student);
            System.out.println("修改结果: " + result); // 添加日志
            return result > 0 ? "success" : "fail";
        } catch (Exception e) {
            System.err.println("修改学生信息时发生错误: " + e.getMessage()); // 添加详细错误信息
            e.printStackTrace();
            return "fail";
        }
    }
}