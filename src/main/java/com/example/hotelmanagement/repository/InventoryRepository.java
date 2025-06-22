package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
