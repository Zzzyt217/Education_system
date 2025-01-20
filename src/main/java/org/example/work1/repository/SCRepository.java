package org.example.work1.repository;

import org.example.work1.entity.SC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SCRepository extends JpaRepository<SC, Long> {
    @Query(value = "SELECT DISTINCT s.username, st.classname, st.phone " +
           "FROM SC s " +
           "JOIN student st ON s.username = st.username " +
           "WHERE s.courseName = :courseName " +
           "ORDER BY st.classname, s.username", 
           nativeQuery = true)
    List<Object[]> findStudentsByCourse(@Param("courseName") String courseName);

    @Query(value = "SELECT DISTINCT s.courseName, s.courseTime, s.classLocation " +
           "FROM SC s " +
           "WHERE s.courseName IS NOT NULL " +
           "AND s.courseTime IS NOT NULL " +
           "AND s.classLocation IS NOT NULL " +
           "ORDER BY s.courseTime", 
           nativeQuery = true)
    List<Object[]> findSchedule();
} 