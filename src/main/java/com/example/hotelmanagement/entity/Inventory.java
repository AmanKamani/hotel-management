package com.example.hotelmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Inventory is basically total number of rooms available on particular datetime.
 * Means, it will have composite key of HOTEL, ROOM, DATETIME
 * So, Inventory will have multiple entry for same room on different datetime
 */
@Getter
@Setter
@Entity
@Table(
        name = "inventory",
        uniqueConstraints = @UniqueConstraint(
                name = "unique_hotel_room_datetime",
                columnNames = {"hotel_id", "room_id", "date_time"}
        )
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventory extends Common {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * One hotel can have multiple inventory.
     * FetchType lazy means, don't fetch hotel details
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;


    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer bookedCount;

    @Column(nullable = false)
    private Integer totalCount;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal surgeFactor;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price; // room's baseprice * surgefactor

    @Column(nullable = false)
    private String city; // to avoid join query, we are duplicating city field here

    @Column(nullable = false)
    private Boolean closed; // whether room is available or not. Closed=true means, not available
}
