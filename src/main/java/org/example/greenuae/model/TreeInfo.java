package org.example.greenuae.model;

import jakarta.persistence.*;
import lombok.Data;
import org.example.greenuae.security.AES;

@Data
@Entity
@Table(name = "treeInfo")
public class TreeInfo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long info_id;

    @Convert(converter = AES.class)
    @Column(nullable = false)
    private String tree_species;

    @Convert(converter = AES.class)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String info_one;

    @Convert(converter = AES.class)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String info_two;

    @Convert(converter = AES.class)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String info_three;

    @Convert(converter = AES.class)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String info_four;

    @Convert(converter = AES.class)
    @Column(nullable = false)
    private String impact_category;

    @Convert(converter = AES.class)
    @Column(nullable = false)
    private int points;

    @Convert(converter = AES.class)
    @Column(nullable = false)
    private long tree_id;
}