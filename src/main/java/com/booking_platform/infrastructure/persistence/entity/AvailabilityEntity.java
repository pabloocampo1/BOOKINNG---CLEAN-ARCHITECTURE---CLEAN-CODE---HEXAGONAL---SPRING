package com.booking_platform.infrastructure.persistence.entity;

import com.booking_platform.domain.model.AvailabilityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "availability", indexes = {
                @Index(name = "idx_availability_dates", columnList = "start_date,end_date"),
                @Index(name = "idx_availability_property_dates", columnList = "property_id,start_date,end_date"),
                @Index(name = "idx_availability_booking_id", columnList = "booking_id")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "property_id", nullable = false, referencedColumnName = "property_id")
        private PropertyEntity property;

        @Column(name = "start_date", nullable = false)
        private LocalDate startDate;

        @Column(name = "booking_id", nullable = true)
        private Long bookingId;

        @Column(name = "end_date", nullable = false)
        private LocalDate endDate;

        @Enumerated(EnumType.STRING)
        @Column(name = "availability_type", nullable = false)
        private AvailabilityType availabilityType;
}
