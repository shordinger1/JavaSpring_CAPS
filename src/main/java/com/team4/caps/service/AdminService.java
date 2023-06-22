package com.team4.caps.service;


import com.team4.caps.model.Admin;
import com.team4.caps.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }


    public List<Admin> getAllAdmins()
    {
        return adminRepository.findAll();
    }

    public Admin getAdminById(Integer id)
    {
        return adminRepository.findById(id).orElseThrow();
    }

    public Boolean deleteAdminById(Integer id)
    {
        try{
            var Admin=adminRepository.findById(id).orElseThrow();
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
        adminRepository.deleteById(id);
        return true;
    }

    public boolean updateAdminById(Integer id,Admin updatedAdmin)
    {
        if(adminRepository.existsById(id)) {
            var admin = updatedAdmin;
            admin.setId(id);
            adminRepository.save(admin);
            return true;
        }
        return false;
    }

    public Admin createAdmin(Admin Admin) {
        return adminRepository.save(Admin);
    }

    public Admin updateAdmin(Integer id, Admin updatedAdmin) throws Exception {
        if(adminRepository.existsById(id)) {
            var admin = updatedAdmin;
            admin.setId(id);
            return adminRepository.save(admin);
        }
        return null;
    }
}
