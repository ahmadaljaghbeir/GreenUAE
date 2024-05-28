package org.example.greenuae.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "treeInfo")
public class TreeInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long info_id;

    @Column(nullable = false)
    private String tree_species;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String info_one;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String info_two;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String info_three;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String info_four;

    @Column(nullable = false)
    private String impact_category;

    @Column(nullable = false)
    private int points;

    @Column(nullable = false)
    private long tree_id;
}