package com.nusiss.idss.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nusiss.idss.config.CustomException;
import com.nusiss.idss.dto.ImageSyncDTO;
import com.nusiss.idss.dto.UserDTO;
import com.nusiss.idss.po.ImageSync;
import com.nusiss.idss.po.User;
import com.nusiss.idss.po.UserRole;
import com.nusiss.idss.repository.ImageSyncRepository;
import com.nusiss.idss.repository.PermissionRepository;
import com.nusiss.idss.repository.UserRepository;
import com.nusiss.idss.repository.UserRoleRepository;
import com.nusiss.idss.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisCrudService redisCrudService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private ObjectMapper objectMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private ImageSyncRepository imageSyncRepository;

    @Autowired
    private JwtUtils jwtUtils;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public User createUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();

        if (userDTO.getRoleId()!= null) {
            user.setUserId(userDTO.getUserId());
        }
        if (isNotEmpty(userDTO.getUsername())) {
            user.setUsername(userDTO.getUsername());
        }
        if (isNotEmpty(userDTO.getPassword())) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        if (isNotEmpty(userDTO.getEmail())) {
            user.setEmail(userDTO.getEmail());
        }
        if (isNotEmpty(userDTO.getPhoneNumber())) {
            user.setPhoneNumber(userDTO.getPhoneNumber());
        }
        if (isNotEmpty(userDTO.getStatus())) {
            user.setStatus(userDTO.getStatus());
        }
        if (userDTO.getLastLogin() != null) {
            user.setLastLogin(userDTO.getLastLogin());
        }
        if (userDTO.getCreateDatetime() != null) {
            user.setCreateDatetime(userDTO.getCreateDatetime());
        }
        if (userDTO.getUpdateDatetime() != null) {
            user.setUpdateDatetime(userDTO.getUpdateDatetime());
        }
        if (isNotEmpty(userDTO.getCreateUser())) {
            user.setCreateUser(userDTO.getCreateUser());
        }
        if (isNotEmpty(userDTO.getUpdateUser())) {
            user.setUpdateUser(userDTO.getUpdateUser());
        }

        User newUser = userRepository.save(user);
        if(userDTO.getRoleId() != null){
            UserRole userRole = new UserRole();
            userRole.setUserId(newUser.getUserId());
            userRole.setRoleId(userDTO.getRoleId());
            userRoleRepository.save(userRole);
        }


        return newUser;
    }

    private static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public User updateUser(Integer userId, UserDTO userDetails) {
        return userRepository.findById(userId).map(user -> {
            if (userDetails.getUsername() != null && !userDetails.getUsername().isEmpty()) {
                user.setUsername(userDetails.getUsername());
            }
            if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                user.setPassword(userDetails.getPassword());
            }
            if (userDetails.getEmail() != null && !userDetails.getEmail().isEmpty()) {
                user.setEmail(userDetails.getEmail());
            }
            if (userDetails.getPhoneNumber() != null && !userDetails.getPhoneNumber().isEmpty()) {
                user.setPhoneNumber(userDetails.getPhoneNumber());
            }
            if (userDetails.getStatus() != null && !userDetails.getStatus().isEmpty()) {
                user.setStatus(userDetails.getStatus());
            }
            if (userDetails.getLastLogin() != null) {
                user.setLastLogin(userDetails.getLastLogin());
            }
            user.setUpdateDatetime(LocalDateTime.now());

            if (userDetails.getUpdateUser() != null && !userDetails.getUpdateUser().isEmpty()) {
                user.setUpdateUser(userDetails.getUpdateUser());
            }

            if(userDetails.getRoleId() != null){
                UserRole userRole = userRoleRepository.findByUserId(userId);
                if(userRole == null){
                    UserRole userRoleNew = new UserRole();
                    userRoleNew.setUserId(userId);
                    userRoleNew.setRoleId(userDetails.getRoleId());
                    userRoleRepository.save(userRoleNew);
                } else {
                    userRole.setRoleId(userDetails.getRoleId());
                    userRoleRepository.save(userRole);
                }
            }

            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
        userRoleRepository.deleteByUserId(userId);
    }

    public boolean validateUserLogin(String username, String rawPassword) {
        User user = userRepository.findByUsername(username);
        if(passwordEncoder.matches(rawPassword, user.getPassword())){
            try {
                String userJson = objectMapper.writeValueAsString(user);
                //save user info to redis
                redisCrudService.save(user.getUsername(), userJson, 60, TimeUnit.MINUTES);
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

    public Page<UserDTO> searchByUsername(String username, Pageable pageable) {
        Page<UserDTO> userPage;
        try{
            userPage = userRepository.findByUsername(username, pageable);
        }catch (Exception e){
            log.info(e.getMessage(), e);
            throw new CustomException(e.getMessage());
        }

        return userPage;
    }

    @Transactional
    public List<ImageSyncDTO> syncUserImages(HttpServletRequest request) {
        User user = jwtUtils.getCurrentUserInfo(request);
        String userName = user.getUsername();

        //LocalDateTime  updateDatetime = imageSyncRepository.getLastSyncTime(userName);
        //if (updateDatetime == null) {
            // default to a very old date to get all records
            //updateDatetime = LocalDateTime.of(1970, 1, 1, 0, 0);
        //}
        /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDatetime = updateDatetime.format(formatter);*/
        List<ImageSyncDTO> imageSyncDTOs = userRepository.getUserImages(userName);

        ImageSync imageSync = new ImageSync();
        imageSync.setCreateUser(userName);
        imageSync.setUpdateUser(userName);
        //imageSync.setUpdateTime(LocalDateTime.now()); // optional if your entity supports it
        imageSyncRepository.save(imageSync);

        return imageSyncDTOs;
    }

    public Map<String, Object> updateUserImages(List<ImageSyncDTO> imageSyncDTOs, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        User currentUser = jwtUtils.getCurrentUserInfo(request);
        String userName = currentUser.getUsername();

        if (imageSyncDTOs != null && !imageSyncDTOs.isEmpty()) {
            for (ImageSyncDTO imageSyncDTO : imageSyncDTOs) {
                Optional<User> userOption = userRepository.findById(imageSyncDTO.getUserId());
                if (userOption.isPresent()) {
                    User user = userOption.get();
                    if (StringUtils.isNotBlank(imageSyncDTO.getStatusCode())) {
                        user.setStatusCode(imageSyncDTO.getStatusCode());
                    }
                    user.setUpdateUser(userName);
                    user.setUpdateDatetime(LocalDateTime.now());
                    userRepository.save(user);
                }
            }
            response.put("statusCode", 200);
            response.put("message", "Users updated successfully");
        } else {
            response.put("statusCode", 400);
            response.put("message", "No users to update");
        }

        return response;
    }

}
