package com.example.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.inventory.item.Item;
import com.example.inventory.itemInventory.Inventory;
import com.example.inventory.itemInventoryKafka.InventoryKafkaService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApplication.class)
public class InventoryApplicationTests{
    @Autowired
    InventoryKafkaService itemInventoryService;

    @Test
    public void test_producer() {
    	Item item = new Item(new Integer(1),"pen", new Double(10));
        Inventory inventory = new Inventory(new Integer(1), new Integer(11), item);
        assertEquals("success", itemInventoryService.sendInventoryRequest(inventory, "test"));
    }
}