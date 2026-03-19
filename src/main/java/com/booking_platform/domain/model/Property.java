package com.booking_platform.domain.model;

import com.booking_platform.domain.exceptions.UserException.NoPermissionManagePropertyException;
import com.booking_platform.domain.exceptions.property.MaxGuestException;
import com.booking_platform.domain.exceptions.property.PropertyStateException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Property {
    private final Long propertyId;
    private String title;
    private String description;
    private PropertyType type;
    private Location location;
    private BigDecimal pricePerNight;
    private Long userId;
    private int maxGuests;
    private int bedrooms;
    private int beds;
    private int bathrooms;
    private boolean isPublished;

    private Set<Amenity> amenities;
    private List<String> houseRules;
    private String cancellationPolicy;
    private List<String> photos;

    private PropertyStatus propertyStatus;
    private double rating;
    private Integer totalReviews;

    public Property(Long propertyId,
            String title,
            String description,
            PropertyType type,
            Location location,
            BigDecimal pricePerNight,
            int maxGuests,
            int bedrooms,
            int beds,
            Long userId,
            int bathrooms,
            Set<Amenity> amenities,
            List<String> houseRules,
            String cancellationPolicy,
            List<String> photos,
            double rating,
            Integer totalReviews) {
        this.propertyId = propertyId;
        this.title = title;
        this.description = description;
        this.type = type;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.maxGuests = maxGuests;
        this.bedrooms = bedrooms;
        this.beds = beds;
        this.bathrooms = bathrooms;
        this.amenities = amenities;
        this.houseRules = houseRules;
        this.cancellationPolicy = cancellationPolicy;
        this.photos = photos;
        this.userId = userId;
        this.propertyStatus = PropertyStatus.IN_REVIEW;
        this.isPublished = false;
        this.rating = rating;
        this.totalReviews = totalReviews;
    }

    public void addStar(int stars) {
        if (stars < 0 || stars > 5) {
            // create a new exception
            return;
        }

        double totalStars = (this.rating * this.totalReviews) + stars;
        this.totalReviews++;
        this.rating = this.rating / totalStars;

        this.rating = Math.round(this.rating * 10.0) / 10.0;

    }

    public void checkOwnerProperty(Long userId) {
        if (!Objects.equals(userId, this.userId)) {
            throw new NoPermissionManagePropertyException("No estas permitido para esta accion.");
        }
    }

    public void checkIfCanBooking() {
        if (!this.propertyStatus.equals(PropertyStatus.ACTIVE) || !this.isPublished) {
            throw new IllegalStateException("La propiedad no esta disponible para reservar.");
        }
    }

    public String getFullAddress() {
        return String.format("%s, %s, %s", location.getCountry(), location.getCity(), location.getAddress());
    }

    public void activate() {
        if (propertyStatus.equals(PropertyStatus.ACTIVE)) {
            throw new PropertyStateException("La propiedad ya esta activa.");
        }

        this.propertyStatus = PropertyStatus.ACTIVE;
    }

    public void changePropertyStatus(PropertyStatus propertyStatus) {
        this.propertyStatus = propertyStatus;
    }

    public void inactive() {
        if (propertyStatus.equals(PropertyStatus.INACTIVE)) {
            throw new PropertyStateException("La propieda ya esta inactiva");
        }

        this.propertyStatus = PropertyStatus.INACTIVE;
    }

    public void published() {
        this.isPublished = true;
    }

    public void unpublished() {
        this.isPublished = false;
    }

    public void validateInformation() {

        if (this.title == null) {
            throw new IllegalArgumentException("título no puede ser nulo");
        }
        if (this.description == null) {
            throw new IllegalArgumentException("descripción no puede ser nula");
        }
        if (this.type == null) {
            throw new IllegalArgumentException("tipo no puede ser nulo");
        }
        if (this.location == null) {
            throw new IllegalArgumentException("ubicación no puede ser nula");
        }
        if (this.pricePerNight == null) {
            throw new IllegalArgumentException("pricePerNight no puede ser null");
        }
        if (this.amenities == null) {
            throw new IllegalArgumentException("amenities no puede ser null");
        }
        if (this.houseRules == null) {
            throw new IllegalArgumentException("houseRules no puede ser null");
        }
        if (this.cancellationPolicy == null) {
            throw new IllegalArgumentException("cancellationPolicy no puede ser null");
        }
        if (propertyStatus == null) {
            throw new IllegalArgumentException("propertyStatus no puede ser null");
        }
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public BigDecimal calculatePrice(int nights) {
        return this.pricePerNight.multiply(BigDecimal.valueOf(nights));
    }

    public Long getUserId() {
        return userId;
    }

    public void validateTotalGuest(int totalGuest) {
        if (totalGuest > this.maxGuests)
            throw new MaxGuestException(this.maxGuests);
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /// GETTER AND SETTER
    ///

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PropertyType getType() {
        return type;
    }

    public void setType(PropertyType type) {
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public String getPropertyTypeName() {
        return type != null ? type.name() : "N/A";
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public Set<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(Set<Amenity> amenities) {
        this.amenities = amenities;
    }

    public List<String> getHouseRules() {
        return houseRules;
    }

    public void setHouseRules(List<String> houseRules) {
        this.houseRules = houseRules;
    }

    public String getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public PropertyStatus getPropertyStatus() {
        return propertyStatus;
    }

    public void setPropertyStatus(PropertyStatus propertyStatus) {
        this.propertyStatus = propertyStatus;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Integer getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Integer totalReviews) {
        this.totalReviews = totalReviews;
    }

    public String getPropertySummary() {
        return String.format(
                "--- %s ---\n" +
                        "Ubicación: %s\n" +
                        "Distribución: %d habitaciones, %d camas y %d baños\n" +
                        "Capacidad máxima: %d personas\n" +
                        "Precio por noche: %s\n" +
                        "Servicios: %s\n" +
                        "Política de cancelación: %s",
                title.toUpperCase(),
                location,
                bedrooms, beds, bathrooms,
                maxGuests,
                pricePerNight.toString(),
                amenities != null ? String.join(", ", amenities.stream().map(Object::toString).toList()) : "N/A",
                cancellationPolicy);
    }
}
