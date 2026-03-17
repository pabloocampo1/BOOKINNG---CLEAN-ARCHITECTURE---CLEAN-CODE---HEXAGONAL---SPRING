package com.booking_platform.infrastructure.persistence.entity;

import com.booking_platform.domain.model.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "property")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id")
    private Long propertyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id")
    private UserEntity user;


    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private PropertyType type;


    @Embedded
    private Location location;

    @Column(precision = 10, scale = 2)
    private BigDecimal pricePerNight;

    private int maxGuests;
    private int bedrooms;
    private int beds;
    private int bathrooms;

    private double rating = 0.0;

    private Integer totalReviews = 0;

    @Column(name = "is_published")
    private boolean isPublished;

    @Enumerated(EnumType.STRING)
    private PropertyStatus propertyStatus;


    @ElementCollection(targetClass = Amenity.class)
    @CollectionTable(name = "property_amenities", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "amenity_name", length = 50)
    @Enumerated(EnumType.STRING)
    private Set<Amenity> amenities;


    @ElementCollection
    @CollectionTable(name = "property_house_rules", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "rule")
    private List<String> houseRules;

    private String cancellationPolicy;

    @ElementCollection
    @CollectionTable(name = "property_photos", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "photo_url")
    private List<String> photos;
}
