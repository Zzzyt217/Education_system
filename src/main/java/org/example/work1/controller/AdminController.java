package org.example.work1.controller;


import org.example.work1.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AdminController {


    @Autowired
    private AdminService adminService;


    @GetMapping("/admin/count")
    public AdminStats getAdminStats() {
        long adminCount = adminService.getAdminCount();
        return new AdminStats(adminCount);
    }


}


class AdminStats {
    private long adminCount;


    public AdminStats(long adminCount) {
        this.adminCount = adminCount;
    }


    public long getAdminCount() {
        return adminCount;
    }


}