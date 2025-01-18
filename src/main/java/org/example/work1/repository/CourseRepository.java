package org.example.work1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.example.work1.entity.Course;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT COUNT(c) FROM Course c")
    Long countAllCourses();
    // 假设你要统计教师的数量，这里需要自定义查询
    @Query("SELECT COUNT(DISTINCT c.teacherName) FROM Course c")
    Long countTeachers();
    // 使用JPQL查询不同的教师名称
    @Query("SELECT DISTINCT c.teacherName FROM Course c")
    List<String> findDistinctTeacherIds(); // 修改返回类型为 List<String>
}