package com.example.inventory.report;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

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
        int count = getCount(path);            
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\InventoryReport" + count + ".html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\InventoryReport" + count + ".pdf");
        }
        count++;
        putCount(count,path);
        return "Report generated in path : " + path;
    }
    
    public int getCount(String path) {
        int count = 0;
        try {
            if ( !new File(path + "\\myCount.txt").exists())
                return 1;
            else {
                BufferedReader br = new BufferedReader(new FileReader(new File(path+"\\myCount.txt")));
                count = Integer.parseInt(br.readLine());
                br.close();
            }                
        } catch(Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public void putCount(int count, String path) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path + "\\myCount.txt")));
            bw.write(Integer.toString(count));
            bw.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}