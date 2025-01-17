package org.example.work1.mapper;

import org.apache.ibatis.annotations.*;
import org.example.work1.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentMapper {
    @Select("SELECT id, username, sex, birthday, classname as className, college, phone, address, status, createTime, roleId FROM student")
    List<Student> getAllStudents();

    @Delete("DELETE FROM student WHERE id = #{id}")
    int deleteStudent(@Param("id") Long id);

    @Insert("INSERT INTO student(username, sex, birthday, phone, address, note, status, classname, create_time, role_id) " +
            "VALUES(#{username}, #{sex}, #{birthday}, #{phone}, #{address}, #{note}, #{status}, #{className}, #{createTime}, #{roleId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addStudent(Student student);

    @Select("<script>" +
            "SELECT id, username, sex, birthday, classname as className, college, phone, address, status, createTime, roleId " +
            "FROM student " +
            "<where>" +
            "<if test='keyword != null and keyword != \"\"'>" +
            "AND (username LIKE CONCAT('%',#{keyword},'%') OR phone LIKE CONCAT('%',#{keyword},'%'))" +
            "</if>" +
            "<if test='className != null and className != \"\"'>" +
            "AND classname = #{className}" +
            "</if>" +
            "<if test='status != null'>" +
            "AND status = #{status}" +
            "</if>" +
            "</where>" +
            "</script>")
    List<Student> searchStudents(@Param("keyword") String keyword,
                                 @Param("className") String className,
                                 @Param("status") Integer status);
}