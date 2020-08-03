package com.example.inventory.itemInventoryKafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.inventory.item.Item;
import com.example.inventory.itemInventory.Inventory;
import com.example.inventory.itemInventory.InventoryRepository;
import com.google.gson.Gson;

@Service
public class InventoryKafkaService {

    @Autowired
    private Gson gson;
    
	@Autowired
	private InventoryRepository inventoryRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InventoryKafkaController.class);

    @Value(value = "${message.topic.name}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, Inventory> itemKafkaTemplate;

    public String sendInventoryRequest(Inventory itemInventory, String correlationId) {
        String payload = gson.toJson(itemInventory);
        Message<String> message = MessageBuilder.withPayload(payload).setHeader(KafkaHeaders.TOPIC,"AddTopic").setHeader("correlationId", String.valueOf(correlationId).getBytes()).build();
        itemKafkaTemplate.send(message);
        LOGGER.info("Sending payload='{}' to topic='{}'", payload, "AddTopic");
        return "success";
    }
    
    public String sendCheckOutRequest(Inventory itemInventory, String correlationId) {
        String payload = gson.toJson(itemInventory);
        Message<String> message = MessageBuilder.withPayload(payload).setHeader(KafkaHeaders.TOPIC,"CheckOutTopic").setHeader("correlationId", String.valueOf(correlationId).getBytes()).build();
        itemKafkaTemplate.send(message);
        LOGGER.info("Sending payload='{}' to topic='{}'", payload, "CheckOutTopic");
        return "success";
    }

    @KafkaListener(topics = "AddTopic", groupId = "ItemInventory", containerFactory = "itemInventoryKafkaListenerContainerFactory")
    public void receiveItemInventoryRequest(@Payload String message, @Headers MessageHeaders messageHeaders)
            throws Exception {
        if (StringUtils.isEmpty(message)) {
            return;
        }
        LOGGER.info("Received payload='{}' from topic='{}'", message, "AddTopic");
        Inventory inventory = gson.fromJson(message, Inventory.class);
        if(inventoryRepository.existsById(inventory.getId())) {
        	Inventory prevInventory = inventoryRepository.findById(inventory.getId()).get();
    		prevInventory.setStock(inventoryRepository.findById(inventory.getId()).get().getStock() + inventory.getStock());
    		inventoryRepository.save(prevInventory);
    		LOGGER.info("Item exists in Inventory. Quantity updated");
        }
        else {
        	inventoryRepository.save(inventory);  
    		LOGGER.info("New item added to Inventory");
        }
    }

	@KafkaListener(topics = "CheckOutTopic", groupId = "ItemInventoryCheckOut", containerFactory = "itemInventoryCheckOutKafkaListenerContainerFactory")
	public void receiveCheckOutRequest(@Payload String message, @Headers MessageHeaders messageHeaders)
	        throws Exception {
	    if (StringUtils.isEmpty(message)) {
	        return;
	    }
        LOGGER.info("Received payload='{}' from topic='{}'", message, "CheckOutTopic");
	    Inventory inventory = gson.fromJson(message, Inventory.class);
	    Inventory prevInventory = inventoryRepository.findById(inventory.getId()).get();
	    if(prevInventory.getStock()<inventory.getStock()) {
	        LOGGER.info("Request can't be satisfied. Required='{}' Available='{}'", inventory.getStock(), inventoryRepository.findById(inventory.getId()).get().getStock());
	    }
	    else {
	    	
	    	prevInventory.setStock((prevInventory.getStock())-inventory.getStock());
			inventoryRepository.save(prevInventory);
	        LOGGER.info("Request satisfied. Stock updated");
	    }
	}
}




