package org.example.work1.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.work1.entity.Admin;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdminxiugaiMapper {
    @Select("SELECT * FROM admin_info WHERE username = #{username} AND userpass = #{password}")
    Admin findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Update("UPDATE admin_info SET userpass = #{password} WHERE username = #{username}")
    int updateAdminPassword(@Param("username") String username, @Param("password") String password);
} 