package org.example.work1.controller;

import org.example.work1.entity.Admin;
import org.example.work1.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getAdminCount() {
        long count = adminRepository.count();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin) {
        Admin savedAdmin = adminRepository.save(admin);
        return new ResponseEntity<>(savedAdmin, HttpStatus.CREATED);
    }

    @GetMapping("/{id}") // 新增获取单个管理员的接口
    public ResponseEntity<Admin> getAdminById(@PathVariable int id) {
        return adminRepository.findById(id)
                .map(admin -> new ResponseEntity<>(admin, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable int id, @RequestBody Admin admin) {
        return adminRepository.findById(id)
                .map(existingAdmin -> {
                    existingAdmin.setUsername(admin.getUsername());
                    existingAdmin.setUserpass(admin.getUserpass());
                    existingAdmin.setNickname(admin.getNickname());
                    existingAdmin.setRoleId(admin.getRoleId());
                    existingAdmin.setRoleName(admin.getRoleName());
                    existingAdmin.setSex(admin.getSex());
                    existingAdmin.setPhone(admin.getPhone());
                    existingAdmin.setEmail(admin.getEmail());
                    Admin updatedAdmin = adminRepository.save(existingAdmin);
                    return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteAdmin(@PathVariable int id) {
        return adminRepository.findById(id)
                .map(admin -> {
                    adminRepository.delete(admin);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Admin>> searchAdmins(@RequestParam String keyword) {
        try {
            List<Admin> admins = adminRepository.searchAdmins(keyword);
            return new ResponseEntity<>(admins, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}