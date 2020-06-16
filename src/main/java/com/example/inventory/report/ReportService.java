package com.example.inventory.report;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.example.inventory.item.ItemRepository;
import com.example.inventory.itemInventory.Inventory;
import com.example.inventory.itemInventory.InventoryRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReportService {

    @Autowired
    private InventoryRepository inventoryRepository;


    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "C:\\Users\\sayee\\Desktop\\Reports";
        List<Report> InventoryReport = new ArrayList<>();
        for(Inventory i:inventoryRepository.findAll()) {
        	Report report = new Report(i.getId(),i.getItem().getName() , i.getItem().getCost() ,i.getStock());
        	InventoryReport.add(report);
        }      
        File file = ResourceUtils.getFile("classpath:Inventory.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(InventoryReport);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "s");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\InventoryReport.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\InventoryReport.pdf");
        }
        return "report generated in path : " + path;
    }
}