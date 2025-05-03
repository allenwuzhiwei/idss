package com.nusiss.idss.po;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "Image_Sync")
public class ImageSync extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
}
