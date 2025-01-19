package org.example.work1.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.work1.entity.Teacher;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TeacherxiugaiMapper {
    @Select("SELECT * FROM teacher WHERE username = #{username} AND password = #{password}")
    Teacher findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Select("SELECT COUNT(*) FROM teacher")
    int count();

    @Update("UPDATE teacher SET password = #{password} WHERE username = #{username}")
    int updateTeacherPassword(@Param("username") String username, @Param("password") String password);
} 