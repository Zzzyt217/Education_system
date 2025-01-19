package org.example.work1.mapper;

import org.apache.ibatis.annotations.*;
import org.example.work1.entity.Teacher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeacherMapper {
    @Select("SELECT * FROM teacher WHERE username = #{username} AND password = #{password}")
    Teacher findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Select("SELECT COUNT(*) FROM teacher")
    int count();

    // 新增方法：获取学院数
    @Select("SELECT COUNT(DISTINCT college) FROM teacher")
    int countColleges();

    // 新增方法：获取所有教师信息
    @Select("SELECT * FROM teacher")
    List<Teacher> findAllTeachers();

    @Select("SELECT * FROM teacher WHERE username LIKE CONCAT('%',#{keyword},'%') " +
            "OR phone LIKE CONCAT('%',#{keyword},'%') " +
            "OR college LIKE CONCAT('%',#{keyword},'%')")
    List<Teacher> searchTeachers(@Param("keyword") String keyword);

    // 新增方法：修改教师信息
    @Update("UPDATE teacher SET username = #{username}, sex = #{sex}, birthday = #{birthday}, jobDate = #{jobDate}, college = #{college}, phone = #{phone}, roleId = #{roleId} WHERE id = #{id}")
    int updateTeacher(Teacher teacher);

    // 新增方法：删除教师信息
    @Delete("DELETE FROM teacher WHERE id = #{id}")
    int deleteTeacher(@Param("id") Long id);

    @Select("SELECT * FROM teacher WHERE id = #{id}")
    Teacher findById(@Param("id") Long id);

    @Insert("INSERT INTO teacher(username, sex, birthday, jobDate, college, phone, roleId, password) " +
            "VALUES(#{username}, #{sex}, #{birthday}, #{jobDate}, #{college}, #{phone}, #{roleId}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertTeacher(Teacher teacher);
}