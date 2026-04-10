package com.booking_platform.mother;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.booking_platform.domain.model.Amenity;
import com.booking_platform.domain.model.CancellationPolicy;
import com.booking_platform.domain.model.Location;
import com.booking_platform.domain.model.Property;
import com.booking_platform.domain.model.PropertyType;

public class PropertyMother {

  public static Property createPropertyWithOwner(Long ownerId) {
    Long propertyId = 1L;
    String title = "My Property";
    String description = "This is my property";
    PropertyType type = PropertyType.APARTMENT;
    Location location = new Location("Country", "City", "Address");
    BigDecimal pricePerNight = new BigDecimal("100");
    Long userId = ownerId;
    int maxGuests = 4;
    int bedrooms = 2;
    int beds = 1;
    int bathrooms = 1;
    Set<Amenity> amenities = Set.of(Amenity.WIFI, Amenity.TV);
    List<String> houseRules = List.of("No smoking", "No pets");
    CancellationPolicy cancellationPolicy = CancellationPolicy.FLEXIBLE;
    List<String> photos = List.of("photo1.jpg", "photo2.jpg");
    double rating = 0.0;
    Integer totalReviews = 0;

    Property property = new Property(propertyId, title, description, type, location, pricePerNight, maxGuests, bedrooms,
        beds, userId, bathrooms, amenities, houseRules, cancellationPolicy, photos, rating, totalReviews);

    return property;

  }

  public static Property createPropertyWitDataNull() {
    Long propertyId = 1L;
    String title = null;
    String description = null;
    PropertyType type = PropertyType.APARTMENT;
    Location location = new Location("Country", "City", "Address");
    BigDecimal pricePerNight = new BigDecimal("100");
    Long userId = 23L;
    int maxGuests = 4;
    int bedrooms = 0;
    int beds = 1;
    int bathrooms = 1;
    Set<Amenity> amenities = Set.of(Amenity.WIFI, Amenity.TV);
    List<String> houseRules = null;
    CancellationPolicy cancellationPolicy = CancellationPolicy.FLEXIBLE;
    List<String> photos = List.of("photo1.jpg", "photo2.jpg");
    double rating = 0.0;
    Integer totalReviews = 0;

    Property property = new Property(propertyId, title, description, type, location, pricePerNight, maxGuests, bedrooms,
        beds, userId, bathrooms, amenities, houseRules, cancellationPolicy, photos, rating, totalReviews);

    return property;

  }

  public static Property validProperty() {
    Long propertyId = 1L;
    String title = "My Property";
    String description = "This is my property";
    PropertyType type = PropertyType.APARTMENT;
    Location location = new Location("Country", "City", "Address");
    BigDecimal pricePerNight = new BigDecimal("100");
    Long userId = 123L;
    int maxGuests = 4;
    int bedrooms = 2;
    int beds = 1;
    int bathrooms = 1;
    Set<Amenity> amenities = Set.of(Amenity.WIFI, Amenity.TV);
    List<String> houseRules = List.of("No smoking", "No pets");
    CancellationPolicy cancellationPolicy = CancellationPolicy.FLEXIBLE;
    List<String> photos = List.of("photo1.jpg", "photo2.jpg");
    double rating = 0.0;
    Integer totalReviews = 0;

    Property property = new Property(propertyId, title, description, type, location, pricePerNight, maxGuests, bedrooms,
        beds, userId, bathrooms, amenities, houseRules, cancellationPolicy, photos, rating, totalReviews);

    return property;

  }

}
