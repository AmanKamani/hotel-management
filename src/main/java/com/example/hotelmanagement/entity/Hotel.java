package com.example.hotelmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    /**
     * To fetch all the rooms of a hotel. But we have already done a reverse inside Room table with ManyToOne
     * automatically deletes room, because of orphan removal.
     * room entity also has orphan removal for inventory {@link Room#getInventories()}.
     * So, deleting hotel will delete all the rooms & related inventories.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Room> rooms;
}
