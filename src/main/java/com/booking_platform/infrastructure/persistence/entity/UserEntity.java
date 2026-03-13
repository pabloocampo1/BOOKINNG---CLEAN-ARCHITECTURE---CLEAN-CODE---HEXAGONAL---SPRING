package com.booking_platform.infrastructure.persistence.entity;

import com.booking_platform.domain.model.Property;
import com.booking_platform.domain.model.Role;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users",
indexes = {
        @Index(name = "email", columnList = "email"),
        @Index(name = "username", columnList = "username")
})



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private Long dni;

    private LocalDateTime createAT;


    @Column( nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String username;

    private boolean state;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

   @OneToMany(mappedBy = "user")
    private List<PropertyEntity> properties;
}
