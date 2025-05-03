package com.nusiss.idss.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;


@Data
@Getter
@Setter
@AllArgsConstructor
public class UserDTO {

    public UserDTO() {

    }

    private Integer userId;

    private String username;

    private String password;

    private String email;

    private String phoneNumber;

    private String status;

    private LocalDateTime lastLogin;

    private String profilePictureUrl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Singapore")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDatetime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Singapore")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDatetime;

    private String createUser;

    private String updateUser;

    private String roleName;

    private Integer roleId;

    public UserDTO(Integer userId, String username, String email, String phoneNumber, String status,
                   LocalDateTime createDatetime, String createUser, String roleName) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.createDatetime = createDatetime;
        this.createUser = createUser;
        this.roleName = roleName;
    }

    public UserDTO(Integer userId, String username, String email, String phoneNumber, String status,
                   LocalDateTime createDatetime, String createUser, String roleName, Integer roleId) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.createDatetime = createDatetime;
        this.createUser = createUser;
        this.roleName = roleName;
        this.roleId = roleId;
    }
}
