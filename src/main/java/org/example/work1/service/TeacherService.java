package org.example.work1.service;

import org.example.work1.entity.Teacher;
import org.example.work1.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    public int getTeacherCount() {
        return teacherMapper.count();
    }

    public int getCollegeCount() {
        return teacherMapper.countColleges();
    }

    // 新增方法：获取所有教师信息
    public List<Teacher> getAllTeachers() {
        return teacherMapper.findAllTeachers();
    }

    // 新增方法：修改教师信息
    public int updateTeacher(Teacher teacher) {
        return teacherMapper.updateTeacher(teacher);
    }

    // 新增方法：删除教师信息
    public int deleteTeacher(Long id) {
        return teacherMapper.deleteTeacher(id);
    }
}