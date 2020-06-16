package com.example.inventory.itemInventory;

import java.io.FileNotFoundException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory.item.Item;
import com.example.inventory.itemInventoryKafka.InventoryKafkaController;
import com.example.inventory.report.ReportService;

import net.sf.jasperreports.engine.JRException;

@RequestMapping("/Inventory")
@RestController
public class InventoryController {
	@Autowired
	private InventoryService inventoryService;
	@Autowired
    private ReportService service;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InventoryKafkaController.class);
		
	@GetMapping
	public List<Inventory> getAll(){
		LOGGER.info("Received request to get all elements of Inventory");
		return inventoryService.getAll();
	}
	
	@GetMapping("/{id}")
	public Inventory getById(@PathVariable Integer id){
		LOGGER.info("Received request to get Item with id='{}' from Inventory",id);
		return inventoryService.getById(id);
	}
	
	@GetMapping("/{id}/item")
	public Item getItemById(@PathVariable Integer id){
		LOGGER.info("Received request to get details of Item with id='{}' from Inventory",id);
		return inventoryService.getItemById(id);
	}

	@DeleteMapping("/{id}")
	public void deleteItem(@PathVariable Integer id) {
		LOGGER.info("Received request delete Item with id='{}' from Inventory",id);
		inventoryService.removeItem(id);
	}
	
	@PostMapping
	public void addItem(@RequestBody Inventory inventory) {
		LOGGER.info("Received request to add Item='{}' to Inventory",inventory);
		inventoryService.addNewItemToInventory(inventory);
	}
	
	@PutMapping("/{id}/Order/{quantity}")
	public String checkout(@PathVariable Integer quantity, @PathVariable Integer id) {
		LOGGER.info("Received request to Check Out '{}' Items with id='{}' from Inventory",quantity, id);
		return inventoryService.CheckOut(quantity,id); 
	}
	

	@PutMapping("/{id}/Add/{quantity}")
	public String addStockToIventory(@PathVariable Integer quantity, @PathVariable Integer id) {
		LOGGER.info("Received request to add '{}' Items with id='{}' to Inventory",quantity, id);
		return inventoryService.addStockToInventory(quantity,id);
	}
	
	@GetMapping("/report/{format}")
    public String generateReport(@PathVariable String format) throws FileNotFoundException, JRException {
        return service.exportReport(format);
    }
	
}
