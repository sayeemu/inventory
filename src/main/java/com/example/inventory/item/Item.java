package com.example.inventory.item;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.inventory.itemInventory.Inventory;



@Entity
@Table(name="Item")
public class Item {
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="item_id")
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="cost")
	private Double cost;
	
	public int getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Item() {
		super();
	}
	public Item(Integer id, String name, Double cost) {
		super();
		this.id = id;
		this.name = name;
		this.cost = cost;
	}
	
	
}
