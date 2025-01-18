package org.example.work1.mapper;

import org.apache.ibatis.annotations.*;
import org.example.work1.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentMapper {
    @Select("SELECT id, username, sex, birthday, classname as className, college, phone, address, status, createTime, roleId " +
            "FROM student " +
            "ORDER BY college ASC, classname ASC, username ASC")
    List<Student> getAllStudents();

    @Delete("DELETE FROM student WHERE id = #{id}")
    int deleteStudent(@Param("id") Long id);

    @Insert("INSERT INTO student(username, sex, sexc, birthday, classname, college, phone, address, status, createTime, roleId) " +
            "VALUES(#{username}, #{sex}, " +
            "CASE WHEN #{sex} = 1 THEN '男' ELSE '女' END, " +
            "#{birthday}, #{className}, #{college}, " +
            "#{phone}, #{address}, #{status}, #{createTime}, #{roleId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addStudent(Student student);

    @Select("<script>" +
            "SELECT id, username, sex, birthday, classname as className, college, phone, address, status, createTime, roleId " +
            "FROM student " +
            "<where>" +
            "<if test='keyword != null and keyword != \"\"'>" +
            "(username LIKE CONCAT('%',#{keyword},'%') " +
            "OR phone LIKE CONCAT('%',#{keyword},'%') " +
            "OR classname LIKE CONCAT('%',#{keyword},'%') " +
            "OR college LIKE CONCAT('%',#{keyword},'%'))" +
            "</if>" +
            "</where>" +
            "ORDER BY college ASC, classname ASC, username ASC" +
            "</script>")
    @Results({
        @Result(property = "className", column = "classname")
    })
    List<Student> searchStudents(@Param("keyword") String keyword);

    @Select("SELECT id, username, sex, birthday, classname as className, college, phone, address, status, createTime, roleId " +
            "FROM student WHERE id = #{id}")
    Student getStudentById(@Param("id") Long id);

    @Update("UPDATE student SET " +
            "sex = #{sex}, " +
            "sexc = CASE WHEN #{sex} = 1 THEN '男' ELSE '女' END, " +
            "birthday = #{birthday}, " +
            "classname = #{className}, " +
            "college = #{college}, " +
            "phone = #{phone}, " +
            "address = #{address} " +
            "WHERE username = #{username}")
    @Results({
        @Result(property = "className", column = "classname")
    })
    int updateStudent(Student student);
}