package com.example.hotelmanagement.entity;

import com.example.hotelmanagement.entity.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "app_user")
public class User extends Common {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false) // postgre automatically creates index for unique=true
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    /**
     * For collection type field, if we annotate with {@link ElementCollection}. No need of ColumnDefinition.
     * then automatically, new table will be created with user_id & roles column
     * <p>
     * {@link Enumerated} means, to store enum as string/ordinal.
     * If string then it will take name else it will take numbers
     */
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;
}
