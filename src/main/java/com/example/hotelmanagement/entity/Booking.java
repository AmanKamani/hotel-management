package com.example.hotelmanagement.entity;

import com.example.hotelmanagement.entity.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking extends Common {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer roomsCount;

    @Column(nullable = false)
    private LocalDateTime checkInDateTime;

    @Column(nullable = false)
    private LocalDateTime checkOutDateTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    /**
     * One guest can belong to multiple bookings &
     * One booking can have multiple guests. So, ManyToMany.
     * For many-to-many relation, a new table will be generated.
     * Which contains booking_id & guest_id columns
     * <p>
     * here @JoinTable is written just for our info. Even if we don't write,
     * JPA will detect and create the table & columns and create one.
     * <p>
     * If we mention JoinTable, then define it in only one entity.
     * Other entity should just contain ManyToMany if required.
     * Only put JoinTable/JoinColumn at a place, who will own the relation
     */
    @ManyToMany
    @JoinTable(
            name = "booking_guest",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "guest_id")
    )
    private Set<Guest> guests;

}
