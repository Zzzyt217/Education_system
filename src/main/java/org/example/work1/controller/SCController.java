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
            List<Object[]> students = scRepository.findStudentsByCourse(courseName);
            List<Map<String, String>> studentList = new ArrayList<>();
            
            for (Object[] student : students) {
                Map<String, String> studentMap = new HashMap<>();
                studentMap.put("username", (String) student[0]);
                studentMap.put("className", (String) student[1]);
                studentMap.put("phone", (String) student[2]);
                studentList.add(studentMap);
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
            
            for (Object[] schedule : scheduleData) {
                Map<String, String> scheduleMap = new HashMap<>();
                scheduleMap.put("courseName", schedule[0] != null ? schedule[0].toString() : "");
                scheduleMap.put("courseTime", schedule[1] != null ? schedule[1].toString() : "");
                scheduleMap.put("classLocation", schedule[2] != null ? schedule[2].toString() : "");
                scheduleList.add(scheduleMap);
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
            List<Object[]> students = scRepository.findStudentsByCourse(courseName);
            
            if (students.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("学生名单");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("序号");
            headerRow.createCell(1).setCellValue("姓名");
            headerRow.createCell(2).setCellValue("班级");
            headerRow.createCell(3).setCellValue("联系电话");

            int rowNum = 1;
            for (Object[] student : students) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rowNum - 1);
                
                String username = student[0] != null ? (String) student[0] : "";
                String className = student[1] != null ? (String) student[1] : "";
                String phone = student[2] != null ? (String) student[2] : "";
                
                row.createCell(1).setCellValue(username);
                row.createCell(2).setCellValue(className);
                row.createCell(3).setCellValue(phone);
            }

            for (int i = 0; i < 4; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            String filename = String.format("%s-学生名单.xlsx", courseName);
            headers.setContentDispositionFormData("attachment", new String(filename.getBytes("UTF-8"), "ISO-8859-1"));

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(outputStream.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
} 