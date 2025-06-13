package com.example.hotelmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "hotel")
public class Hotel extends Common {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // each hotel should have name
    @Column(nullable = false)
    private String name;

    private String city;

    @Column(columnDefinition = "TEXT[]")
    private String[] photos;

    @Column(columnDefinition = "TEXT[]")
    private String[] amenities;

    @Embedded // fields will be like address, phone_number
    private ContactInfo contactInfo;

    @Column(nullable = false)
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

//    /**
//     * To fetch all the rooms of a hotel. But we have already done a reverse inside Room table with ManyToOne
//     */
//    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
//    private List<Room> rooms;
}
