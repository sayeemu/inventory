package com.example.inventory.itemInventory;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.inventory.item.Item;

@Entity
@Table(name="Inventory")
public class Inventory {
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	@Column(name="stock")
	private Integer stock; 
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id", referencedColumnName = "item_id")
    private Item item;
	
	
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Inventory() {
		super();
	}

	public Inventory(Integer id, Integer stock, Item item) {
		super();
		this.id = id;
		this.stock = stock;
		this.item = item;
	}
	
}
