package com.example.inventory.itemInventory;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.inventory.report.Report;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer>{
	
//	@Query("SELECT new package com.example.inventory.report.Report(v.id, i.name, i.cost, v.stock) FROM INVENTORY v JOIN v.item i")
//	public List<Report> getJoinInformation();
}
