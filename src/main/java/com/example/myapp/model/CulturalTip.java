package com.example.myapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cultural_tips")
public class CulturalTip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(name = "tip_category", nullable = false)
    private String tipCategory; // "Etiquette", "Dress Code"

    @Column(columnDefinition = "TEXT")
    private String content;
}
