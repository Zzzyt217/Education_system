package org.example.work1.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.work1.entity.Student;
import org.example.work1.mapper.StudentMapper;
import org.example.work1.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentMapper studentMapper;

    @GetMapping("/students/stats")
    public StudentStats getStudentStats() {
        long total = studentService.getTotalStudentCount();
        long male = studentService.getMaleStudentCount();
        long female = studentService.getFemaleStudentCount();
        Map<String, Long> collegeDistribution = studentService.getCollegeDistribution();

        return new StudentStats(total, male, female, collegeDistribution);
    }

    @GetMapping("/api/students/export")
    public ResponseEntity<byte[]> exportStudentList() {
        try {
            System.out.println("开始导出学生名单");
            
            List<Student> students = studentMapper.getAllStudents();
            System.out.println("查询到学生数量: " + students.size());
            
            if (students.isEmpty()) {
                System.out.println("警告：没有找到学生数据");
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
            headerRow.createCell(2).setCellValue("性别");
            headerRow.createCell(3).setCellValue("生日");
            headerRow.createCell(4).setCellValue("电话");
            headerRow.createCell(5).setCellValue("地址");
            headerRow.createCell(6).setCellValue("状态");
            headerRow.createCell(7).setCellValue("学院");
            headerRow.createCell(8).setCellValue("班级");

            // 填充数据
            System.out.println("开始填充学生数据");
            int rowNum = 1;
            for (Student student : students) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rowNum - 1);
                row.createCell(1).setCellValue(student.getUsername());
                row.createCell(2).setCellValue(student.getSex() == 1 ? "男" : "女");
                row.createCell(3).setCellValue(student.getBirthday());
                row.createCell(4).setCellValue(student.getPhone());
                row.createCell(5).setCellValue(student.getAddress());
                row.createCell(6).setCellValue(getStatusText(student.getStatus()));
                row.createCell(7).setCellValue(student.getCollege());
                row.createCell(8).setCellValue(student.getClassName());
            }

            // 自动调整列宽
            System.out.println("调整列宽");
            for (int i = 0; i < 9; i++) {
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
            String filename = "学生名单.xlsx";
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

    private String getStatusText(int status) {
        switch (status) {
            case 1: return "在读";
            case 2: return "休学";
            case 3: return "退学";
            default: return "未知";
        }
    }
}

class StudentStats {
    private long total;
    private long male;
    private long female;
    private Map<String, Long> collegeDistribution;

    public StudentStats(long total, long male, long female, Map<String, Long> collegeDistribution) {
        this.total = total;
        this.male = male;
        this.female = female;
        this.collegeDistribution = collegeDistribution;
    }

    public long getTotal() {
        return total;
    }

    public long getMale() {
        return male;
    }

    public long getFemale() {
        return female;
    }

    public Map<String, Long> getCollegeDistribution() {
        return collegeDistribution;
    }
}
