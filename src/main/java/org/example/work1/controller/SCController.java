package org.example.work1.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.work1.repository.SCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
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

    @GetMapping("/export/{courseName}")
    public ResponseEntity<byte[]> exportStudentList(@PathVariable String courseName) {
        try {
            System.out.println("开始导出课程: " + courseName + " 的学生名单");
            
            List<Object[]> students = scRepository.findStudentsByCourse(courseName);
            System.out.println("查询到学生数量: " + students.size());
            
            if (students.isEmpty()) {
                System.out.println("警告：没有找到该课程的学生");
                return ResponseEntity.noContent().build();
            }
            
            // 创建工作簿和工作表
            System.out.println("创建Excel工作簿");
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("学生名单");

            // 创建标题行
            System.out.println("创建标题行");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("序号");
            headerRow.createCell(1).setCellValue("姓名");
            headerRow.createCell(2).setCellValue("班级");
            headerRow.createCell(3).setCellValue("联系电话");

            // 填充数据
            System.out.println("开始填充学生数据");
            int rowNum = 1;
            for (Object[] student : students) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rowNum - 1);
                
                // 添加空值检查
                String username = student[0] != null ? (String) student[0] : "";
                String className = student[1] != null ? (String) student[1] : "";
                String phone = student[2] != null ? (String) student[2] : "";
                
                row.createCell(1).setCellValue(username);
                row.createCell(2).setCellValue(className);
                row.createCell(3).setCellValue(phone);
                
                System.out.println("添加学生数据: " + username + ", " + className + ", " + phone);
            }

            // 自动调整列宽
            System.out.println("调整列宽");
            for (int i = 0; i < 4; i++) {
                sheet.autoSizeColumn(i);
            }

            // 将工作簿写入字节数组
            System.out.println("写入Excel数据到输出流");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();

            // 设置响应头
            System.out.println("设置响应头");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            String filename = String.format("%s-学生名单.xlsx", courseName);
            headers.setContentDispositionFormData("attachment", new String(filename.getBytes("UTF-8"), "ISO-8859-1"));

            System.out.println("导出完成，准备返回文件");
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(outputStream.toByteArray());

        } catch (Exception e) {
            System.err.println("导出过程中发生错误:");
            System.err.println("错误类型: " + e.getClass().getName());
            System.err.println("错误信息: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
} 