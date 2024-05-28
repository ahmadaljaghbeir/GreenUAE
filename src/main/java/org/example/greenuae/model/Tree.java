package org.example.greenuae.model;

import jakarta.persistence.*;
import lombok.Data;
import org.example.greenuae.security.AES;


@Data
@Entity
@Table(name = "trees")
public class Tree {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tree_id;

    @Lob
    @Convert(converter = AES.class)
    @Column(nullable = false, columnDefinition = "LONGBLOB")
    private byte[] tree_photo;

    @Convert(converter = AES.class)
    @Column(nullable = false)
    private String gps_location;

    @Convert(converter = AES.class)
    @Column(nullable = false)
    private int points_rewarded;

    @Convert(converter = AES.class)
    @Column(nullable = false)
    private String planting_date;

    @Convert(converter = AES.class)
    @Column(nullable = false)
    private long user_id;
}