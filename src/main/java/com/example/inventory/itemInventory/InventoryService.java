package com.example.inventory.itemInventory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.inventory.item.Item;

@Service
public class InventoryService {
	@Autowired
	private InventoryRepository inventoryRepository;

	public List<Inventory> getAll() {
		return inventoryRepository.findAll();
	}

	public void addNewItemToInventory(Inventory inventory) {
		inventoryRepository.save(inventory);		
	}

	public void removeItem(Integer id) {
		inventoryRepository.delete(inventoryRepository.findById(id).get());
		
	}

	public String CheckOut(Integer quantity, int id) {
		if(inventoryRepository.findById(id).get().getStock()<quantity) {
			return "Only " + inventoryRepository.findById(id).get().getStock() + " Item_Id " + id + " available"; 
		}
		Inventory inventory = inventoryRepository.findById(id).get();
		inventory.setStock(inventoryRepository.findById(id).get().getStock()-quantity);
		inventoryRepository.save(inventory);
		return "Order received";
	}

	public String addStockToInventory(Integer quantity, Integer id) {
		Inventory inventory = inventoryRepository.findById(id).get();
		inventory.setStock(inventoryRepository.findById(id).get().getStock() + quantity);
		inventoryRepository.save(inventory);
		return "Stock Updated";
	}

	
	public Inventory getById(Integer id) {
		return inventoryRepository.findById(id).get();
	}

	public Item getItemById(Integer id) {
		return inventoryRepository.findById(id).get().getItem();
	}



}
