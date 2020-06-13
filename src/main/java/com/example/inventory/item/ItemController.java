package com.example.inventory.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory.itemInventory.Inventory;

@RequestMapping("/Items")
@RestController
public class ItemController {
	
	@Autowired
	private ItemService itemService;
		
	@GetMapping
	public List<Item> getAll(){
		return itemService.getAll();
	}
	
	@GetMapping("/{id}")
	public Item getItemByName(@PathVariable Integer id) {
		return itemService.getItemById(id);
	}
	
	@PostMapping
	public String addItem(@RequestBody Item item) {
		itemService.addNewItemToInventory(item);
		return "Item added";
	}
	
	@DeleteMapping("/{id}")
	public String deleteItem(@PathVariable Integer id) {
		itemService.removeItem(id);
		return "Item deleted";
	}


}
