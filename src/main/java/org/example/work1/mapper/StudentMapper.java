package org.example.work1.mapper;

import org.apache.ibatis.annotations.*;
import org.example.work1.entity.Student;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentMapper {
    @Select("SELECT * FROM student")
    List<Student> getAllStudents();

    @Delete("DELETE FROM student WHERE id = #{id}")
    int deleteStudent(Long id);

    @Insert("INSERT INTO student(username, sex, birthday, phone, email, address, note, status, level_id, create_time, role_id) " +
            "VALUES(#{username}, #{sex}, #{birthday}, #{phone}, #{email}, #{address}, #{note}, #{status}, #{levelId}, #{createTime}, #{roleId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addStudent(Student student);
}