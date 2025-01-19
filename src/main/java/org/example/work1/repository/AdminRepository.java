package org.example.work1.repository;

import org.example.work1.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    long count();

    @Query("SELECT a FROM Admin a WHERE a.username LIKE CONCAT('%',?1,'%') OR a.phone LIKE CONCAT('%',?1,'%') OR a.nickname LIKE CONCAT('%',?1,'%')")
    List<Admin> searchAdmins(String keyword);
}