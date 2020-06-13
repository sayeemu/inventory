package com.example.inventory.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;

	public void addNewItemToInventory(Item item) {
		itemRepository.save(item);		
	}

	public List<Item> getAll() {
		return itemRepository.findAll();
	}


	public Item getItemById(Integer id) {
		return itemRepository.findById(id).get();
	}

	public void removeItem(Integer id) {
		itemRepository.deleteById(id);
		
	}
	
	




	
}
