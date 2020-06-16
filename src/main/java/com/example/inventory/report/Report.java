package com.example.inventory.report;

public class Report {
	private Integer id;
	private String name;
	private Double cost;
	private Integer stock;
	public Integer getId() {
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
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Report(Integer id, String name, Double cost, Integer stock) {
		super();
		this.id = id;
		this.name = name;
		this.cost = cost;
		this.stock = stock;
	}
	public Report() {
		super();
	}
	
	
}
