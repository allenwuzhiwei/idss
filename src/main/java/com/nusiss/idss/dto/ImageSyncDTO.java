package com.nusiss.idss.dto;

import lombok.*;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageSyncDTO {

    private Integer userId;

    private String profilePictureUrl;

    private String statusCode;

    public ImageSyncDTO(Integer userId, String profilePictureUrl){
        this.profilePictureUrl = profilePictureUrl;
        this.userId = userId;
    }

    public ImageSyncDTO(String statusCode, Integer userId){
        this.statusCode = statusCode;
        this.userId = userId;
    }

}
