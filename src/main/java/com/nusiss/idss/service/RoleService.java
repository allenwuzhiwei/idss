package com.nusiss.idss.service;

import com.nusiss.idss.po.Role;
import com.nusiss.idss.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public List<Role> getAllRoles() {
        return repository.findAll();
    }

    public Optional<Role> getRoleById(String id) {
        return repository.findById(id);
    }

    public Role createRole(Role role) {
        return repository.save(role);
    }

    public Role updateRole(String id, Role role) {
        if (repository.existsById(id)) {
            role.setRoleId(id);
            return repository.save(role);
        }
        return null;
    }

    public boolean deleteRole(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
