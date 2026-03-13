package com.booking_platform.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "amenity_entity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AmenityEntity {


    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;


    
}
