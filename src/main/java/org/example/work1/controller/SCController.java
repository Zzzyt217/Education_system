package org.example.work1.controller;

import org.example.work1.repository.SCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/sc")
public class SCController {

    @Autowired
    private SCRepository scRepository;

    @GetMapping("/students/{courseName}")
    public Map<String, Object> getStudentsByCourse(@PathVariable String courseName) {
        Map<String, Object> result = new HashMap<>();
        try {
            System.out.println("正在查询课程: " + courseName + " 的学生名单");
            List<Object[]> students = scRepository.findStudentsByCourse(courseName);
            List<Map<String, String>> studentList = new ArrayList<>();
            
            System.out.println("查询结果数量: " + students.size());
            for (Object[] student : students) {
                Map<String, String> studentMap = new HashMap<>();
                studentMap.put("username", (String) student[0]);
                studentMap.put("className", (String) student[1]);
                studentMap.put("phone", (String) student[2]);
                studentList.add(studentMap);
                System.out.println("处理学生数据: " + studentMap);
            }
            
            result.put("code", 200);
            result.put("msg", "success");
            result.put("data", studentList);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("msg", "获取学生名单失败: " + e.getMessage());
        }
        return result;
    }

    @GetMapping("/schedule")
    public Map<String, Object> getSchedule() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Object[]> scheduleData = scRepository.findSchedule();
            List<Map<String, String>> scheduleList = new ArrayList<>();
            
            System.out.println("查询到课表数据条数: " + scheduleData.size());
            
            for (Object[] schedule : scheduleData) {
                Map<String, String> scheduleMap = new HashMap<>();
                scheduleMap.put("courseName", schedule[0] != null ? schedule[0].toString() : "");
                scheduleMap.put("courseTime", schedule[1] != null ? schedule[1].toString() : "");
                scheduleMap.put("classLocation", schedule[2] != null ? schedule[2].toString() : "");
                scheduleList.add(scheduleMap);
                
                System.out.println("课程信息: " + scheduleMap);
            }
            
            result.put("code", 200);
            result.put("msg", "success");
            result.put("data", scheduleList);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("msg", "获取课表失败: " + e.getMessage());
        }
        return result;
    }
} 