package com.nusiss.idss.service;

import com.nusiss.idss.po.AlertDeviceDataRelationship;
import com.nusiss.idss.repository.AlertDeviceDataRelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlertDeviceDataRelationshipService {

    @Autowired
    private AlertDeviceDataRelationshipRepository repository;

    public List<AlertDeviceDataRelationship> getAllRelationships() {
        return repository.findAll();
    }

    public Optional<AlertDeviceDataRelationship> getRelationshipById(Integer id) {
        return repository.findById(id);
    }

    public AlertDeviceDataRelationship createRelationship(AlertDeviceDataRelationship relationship) {
        return repository.save(relationship);
    }

    public AlertDeviceDataRelationship updateRelationship(Integer id, AlertDeviceDataRelationship relationship) {
        if (repository.existsById(id)) {
            relationship.setRelationshipId(id);
            return repository.save(relationship);
        }
        return null;
    }

    public boolean deleteRelationship(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
