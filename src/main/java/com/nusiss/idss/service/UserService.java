package com.nusiss.idss.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nusiss.idss.po.User;
import com.nusiss.idss.repository.PermissionRepository;
import com.nusiss.idss.repository.UserRepository;
import com.nusiss.idss.repository.UserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisCrudService redisCrudService;

    @Autowired
    private ObjectMapper objectMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password

        return userRepository.save(user);
    }

    public User updateUser(Integer userId, User userDetails) {
        return userRepository.findById(userId).map(user -> {
            user.setUsername(userDetails.getUsername());
            user.setPassword(userDetails.getPassword());
            user.setEmail(userDetails.getEmail());
            user.setPhoneNumber(userDetails.getPhoneNumber());
            user.setStatus(userDetails.getStatus());
            user.setLastLogin(userDetails.getLastLogin());
            user.setProfilePictureUrl(userDetails.getProfilePictureUrl());
            user.setUpdateDatetime(LocalDateTime.now());
            user.setUpdateUser(userDetails.getUpdateUser());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    public boolean validateUserLogin(String username, String rawPassword) {
        User user = userRepository.findByUsername(username);
        if(passwordEncoder.matches(rawPassword, user.getPassword())){
            try {
                String userJson = objectMapper.writeValueAsString(user);
                //save user info to redis
                redisCrudService.save(user.getUsername(), userJson, 30, TimeUnit.MINUTES);
            } catch (Exception e) {
                log.error("", e);
                log.info(e.getMessage());
                throw new RuntimeException(e);
            }

            return true;
        } else {

            return false;
        }
    }

    public boolean userHasPermission(String userName, String permissionName) {

        return permissionRepository.userHasPermission(userName, permissionName) > 0;
    }

    public void updateLastLogin(String username) {
        /*userRepository.findByUsername(username).ifPresent(user -> {
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
        });*/
    }

    /*public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }*/

    /*public List<String> getUserPermissions(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Find all roles of this user
        List<UserRole> userRoles = userRoleRepository.findByUserId(user.getUserId());
        List<Integer> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());

        // Get all permissions tied to roles
        List<RolePermission> rolePermissions = rolePermissionRepository.findByRoleIdIn(roleIds);
        List<Integer> permissionIds = rolePermissions.stream()
                .map(RolePermission::getPermissionId)
                .distinct()
                .collect(Collectors.toList());

        // Fetch permission names
        List<Permission> permissions = permissionRepository.findByPermissionIdIn(permissionIds);
        return permissions.stream()
                .map(Permission::getPermissionName)
                .distinct()
                .collect(Collectors.toList());
    }*/

    public UserDetails loadUserByUserId(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>() // or authorities if needed
        );
    }

    public List<String> getUserPermissions(String username) {

        return permissionRepository.findPermissionsByUsername(username);
    }


}
