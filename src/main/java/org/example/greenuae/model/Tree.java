package org.example.greenuae.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "trees")
public class Tree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tree_id;

    @Lob
    @Column(nullable = false, columnDefinition = "BLOB")
    //MultipartFile
    private byte[] tree_photo;

    @Column(nullable = false)
    private String gps_location;

    @Column(nullable = false)
    private int points_rewarded;

    @Column(nullable = false)
    private String planting_date;

    @Column(nullable = false)
    private long user_id;

}