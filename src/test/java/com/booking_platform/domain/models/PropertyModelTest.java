package com.booking_platform.domain.models;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.booking_platform.domain.exceptions.UserException.NoPermissionManagePropertyException;
import com.booking_platform.domain.exceptions.property.MaxGuestException;
import com.booking_platform.domain.model.Property;
import com.booking_platform.domain.model.PropertyStatus;
import com.booking_platform.mother.PropertyMother;
import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop;

public class PropertyModelTest {

  @Test
  void shouldAddStars() {
    Property property = PropertyMother.validProperty();

    property.addStar(5);

    assertEquals(5.0, property.getRating());
    assertEquals(property.getTotalReviews(), 1);

    property.addStar(4);

    assertEquals(4.5, property.getRating());
    assertEquals(property.getTotalReviews(), 2);

    property.addStar(3);

    assertEquals(4.0, property.getRating());
    assertEquals(property.getTotalReviews(), 3);

  }

  @Test
  void shouldNotChangeRating_WhenStarsAreInvalid() {
    Property property = PropertyMother.validProperty();
    double currentRating = property.getRating();
    int currentTotalReviews = property.getTotalReviews();

    property.addStar(10);
    property.addStar(-1);

    assertEquals(currentRating, property.getRating());
    assertEquals(currentTotalReviews, property.getTotalReviews());

  }

  @Test
  @DisplayName("Debería lanzar excepción si un intruso intenta gestionar la propiedad")
  void shouldThrowNoPermissionException_WhenUserIsNotTheOwner() {
    Long userid = 13L;
    Property property = PropertyMother.createPropertyWithOwner(userid);

    assertThrows(NoPermissionManagePropertyException.class, () -> property.checkOwnerProperty(14L));

  }

  @Test
  @DisplayName("No deberia de lanzar excepcion si el usuario es el propietario")
  void shouldNotThrowNoPermissionException_WhenUserIsTheOwner() {
    Long userid = 13L;
    Property property = PropertyMother.createPropertyWithOwner(userid);

    assertDoesNotThrow(() -> property.checkOwnerProperty(userid));
    assertEquals(userid, property.getUserId());
  }

  @Test
  @DisplayName("No deberia de lanzar excepcion si la propiedad esta publicada y su estado esta activo")
  void shouldCanBooking_WhenIsPublishedAndAvailable() {
    Property property = PropertyMother.validProperty();
    property.setPropertyStatus(PropertyStatus.ACTIVE);
    property.setPublished(true);

    assertDoesNotThrow(() -> property.checkIfCanBooking());
  }

  @Test
  @DisplayName("No deberia de lanzar excepcion si la propiedad  esta publicada y su estado NO esta activo")
  void shouldCanBooking_WhenIsPublishedAndNotAvailable() {
    Property property = PropertyMother.validProperty();
    property.setPropertyStatus(PropertyStatus.INACTIVE);
    property.setPublished(true);

    assertThrows(IllegalStateException.class, () -> property.checkIfCanBooking());
  }

  @Test
  @DisplayName("No deberia de lanzar excepcion si la propiedad esta publicada y su estado esta activo")
  void shouldCanBooking_WhenIsNotPublishedAndAvailable() {
    Property property = PropertyMother.validProperty();
    property.setPropertyStatus(PropertyStatus.ACTIVE);
    property.setPublished(false);

    assertThrows(IllegalStateException.class, () -> property.checkIfCanBooking());
  }

  @Test
  @DisplayName("Deberia dejar activar una propiedad.")
  void shouldChangeTheStateOfTheProperty() {
    Property property = PropertyMother.validProperty();

    property.activate();

    assertEquals(PropertyStatus.ACTIVE, property.getPropertyStatus());
  }

  @Test
  @DisplayName("Deberia dejar desactivar una propiedad.")
  void shouldChangeTheStateOfThePropertyToInactive() {
    Property property = PropertyMother.validProperty();

    property.inactive();

    assertEquals(PropertyStatus.INACTIVE, property.getPropertyStatus());
  }

  @Test
  @DisplayName("Deberia dejar cambiar el estado de una propiedad.")
  void shouldChangeThePropertyStatus() {
    Property property = PropertyMother.validProperty();

    PropertyStatus propertyStatusActive = PropertyStatus.ACTIVE;
    PropertyStatus propertyStatusInactive = PropertyStatus.INACTIVE;
    PropertyStatus propertyStatusInReview = PropertyStatus.IN_REVIEW;

    property.changePropertyStatus(propertyStatusActive);
    assertEquals(propertyStatusActive, property.getPropertyStatus());

    property.changePropertyStatus(propertyStatusInactive);
    assertEquals(propertyStatusInactive, property.getPropertyStatus());

    property.changePropertyStatus(propertyStatusInReview);
    assertEquals(propertyStatusInReview, property.getPropertyStatus());

  }

  @Test
  @DisplayName("Deberia dejar publicar una propidad.")
  void shouldChangeThePropertyPublished() {
    Property property = PropertyMother.validProperty();

    property.published();

    assertTrue(property.isPublished());
  }

  @Test
  @DisplayName("Deberia dejar despublicar una propidad.")
  void shouldChangeThePropertyUnpublished() {
    Property property = PropertyMother.validProperty();

    property.unpublished();

    assertFalse(property.isPublished());

  }

  @Test
  @DisplayName("Deberia dejar cambiar el estado de una propiedad.")
  void shouldCalculatePricePerNight() {
    Property property = PropertyMother.validProperty();

    // first price
    property.setPricePerNight(new BigDecimal("3000"));
    BigDecimal totalPrice = property.calculatePrice(2);

    assertEquals(new BigDecimal("6000"), totalPrice);

    // second price
    property.setPricePerNight(new BigDecimal("2500"));
    totalPrice = property.calculatePrice(3);
    assertEquals(new BigDecimal("7500"), totalPrice);

    // third price
    property.setPricePerNight(new BigDecimal("5300"));
    totalPrice = property.calculatePrice(4);
    assertEquals(new BigDecimal("21200"), totalPrice);

  }

  @Test
  @DisplayName("Deberia lanzar una excepcion por limite de huspeds")
  void shouldThrowException_WhenMaxHuespedIsExceeded() {

    Property property = PropertyMother.validProperty();
    property.setMaxGuests(2);

    assertThrows(MaxGuestException.class, () -> property.validateTotalGuest(6));

  }

  @Test
  @DisplayName("No deberia lanzar una excepcion por limite de huspeds")
  void shouldNotThrowException_WhenMaxHuespedIsNotExceeded() {

    Property property = PropertyMother.validProperty();
    property.setMaxGuests(2);

    assertDoesNotThrow(() -> property.validateTotalGuest(2));

  }

  @Test
  @DisplayName("Deberia lanzar excepcion si algun dato de la propiedad es null")
  void shouldThrowException_WhenPropertyDataIsNull() {
    Property property = PropertyMother.createPropertyWitDataNull();

    assertThrows(IllegalArgumentException.class, () -> property.validateInformation());
  }

  @Test
  @DisplayName("Deberia lanzar excepcion si algun dato de la propiedad es null")
  void shouldThrowException_WhenPropertyDataIsCorrect() {
    Property property = PropertyMother.validProperty();

    assertDoesNotThrow(() -> property.validateInformation());
  }
}
