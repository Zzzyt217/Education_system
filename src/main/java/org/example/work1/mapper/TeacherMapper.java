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

    // 新增方法：修改教师信息
    @Update("UPDATE teacher SET username = #{username}, sex = #{sex}, birthday = #{birthday}, jobDate = #{jobDate}, college = #{college}, password = #{password}, phone = #{phone}, roleId = #{roleId} WHERE id = #{id}")
    int updateTeacher(Teacher teacher);

    // 新增方法：删除教师信息
    @Delete("DELETE FROM teacher WHERE id = #{id}")
    int deleteTeacher(Long id);
}