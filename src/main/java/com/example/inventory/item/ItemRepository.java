package com.example.inventory.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>{

	Item findByName(String name);

	boolean existsByName(String name);
}


