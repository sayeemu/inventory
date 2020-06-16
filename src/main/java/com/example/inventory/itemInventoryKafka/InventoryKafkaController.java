package com.example.inventory.itemInventoryKafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.inventory.itemInventory.Inventory;

@RequestMapping("/Kafka/Inventory")
@RestController
public class InventoryKafkaController {
	@Autowired
    InventoryKafkaService itemInventoryService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InventoryKafkaController.class);
	
	@PostMapping("/Add")
	public void addStockInventory(@RequestBody Inventory inventory) {
		LOGGER.info("Received request to add stock to Inventory='{}' using Kafka", inventory);
		itemInventoryService.sendInventoryRequest(inventory, "test");
	}
	
	@PutMapping("/Order")
	public void checkOut(@RequestBody Inventory inventory) {
		LOGGER.info("Received request for CheckOut='{}' using Kafka", inventory);
		itemInventoryService.sendCheckOutRequest(inventory, "test");
	}
	
}
