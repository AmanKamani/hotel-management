package com.example.hotelmanagement.entity;

import com.example.hotelmanagement.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "guest")
public class Guest extends Common {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Multiple Guest belongs to a user.
     * Means, one user can book hotel for multiple people(guests)
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private Integer age;

    // Just FYI. We don't have use case
    // we will only need all guests of a booking. Because,
    // the person who is booking the room, can see all the guests for its booking
    // here we have to put mappedBy, because,
    // this is not owning the relation. Whoever owns the relation, that entity will use @JoinColumn/@JoinTable.
//    @ManyToMany(mappedBy = "guests")
//    private Set<Booking> bookings;
}
