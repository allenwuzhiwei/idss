package com.nusiss.idss.service;

import com.nusiss.idss.po.UserDeviceRelationship;
import com.nusiss.idss.repository.UserDeviceRelationshipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDeviceRelationshipService {

    private final UserDeviceRelationshipRepository repository;

    public UserDeviceRelationshipService(UserDeviceRelationshipRepository repository) {
        this.repository = repository;
    }

    public List<UserDeviceRelationship> getAll() { return repository.findAll(); }
    public UserDeviceRelationship getById(String id) { return repository.findById(id).orElse(null); }
    public UserDeviceRelationship save(UserDeviceRelationship rel) { return repository.save(rel); }
    public void delete(String id) { repository.deleteById(id); }
}
