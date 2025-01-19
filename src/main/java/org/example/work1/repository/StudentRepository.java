package org.example.work1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.example.work1.entity.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    long countBySex(int sex);

    @Query("SELECT s.college, COUNT(s) FROM Student s GROUP BY s.college")
    List<Object[]> getCollegeCounts();
}
