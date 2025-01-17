package org.example.work1.service.impl;
import org.example.work1.repository.AdminRepository;
import org.example.work1.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public long getAdminCount() {
        return adminRepository.count();
    }
}
