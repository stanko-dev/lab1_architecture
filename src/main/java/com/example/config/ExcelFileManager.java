// File: com/example/config/ExcelFileManager.java
package com.example.config;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class ExcelFileManager {
    private static volatile ExcelFileManager instance;
    private final ExcelDatabaseConfig config;

    private ExcelFileManager(ExcelDatabaseConfig config) {
        this.config = config;
        initializeWorkbook();
    }

    public static ExcelFileManager getInstance(ExcelDatabaseConfig config) {
        if (instance == null) {
            synchronized (ExcelFileManager.class) {
                if (instance == null) {
                    instance = new ExcelFileManager(config);
                }
            }
        }
        return instance;
    }

    public XSSFWorkbook openWorkbook() throws IOException {
        try (InputStream inputStream = Files.newInputStream(config.getFilePath())) {
            return new XSSFWorkbook(inputStream);
        }
    }

    public String getSheetName() {
        return config.getSheetName();
    }

    private void initializeWorkbook() {
        if (Files.exists(config.getFilePath())) {
            return;
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(config.getSheetName());
            createHeader(sheet.createRow(0));

            createRow(sheet.createRow(1), 201, "Eva Hart", "Administration", "OFFICE_CLERK",
                    0.0, 0.0, 21.0, 160, 0.0, 0.0, 0, 0.0);
            createRow(sheet.createRow(2), 202, "Felix North", "Administration", "MANAGER",
                    5800.0, 800.0, 0.0, 0, 0.0, 0.0, 0, 0.0);

            try (OutputStream outputStream = Files.newOutputStream(config.getFilePath())) {
                workbook.write(outputStream);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to initialize Excel file", exception);
        }
    }

    private void createHeader(Row row) {
        String[] headers = {
                "id", "name", "department", "type", "base_salary", "bonus", "hourly_rate",
                "hours_per_month", "commission_rate", "monthly_sales", "on_call_hours", "on_call_rate"
        };

        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
        }
    }

    private void createRow(
            Row row,
            int id,
            String name,
            String department,
            String type,
            double baseSalary,
            double bonus,
            double hourlyRate,
            int hoursPerMonth,
            double commissionRate,
            double monthlySales,
            int onCallHours,
            double onCallRate
    ) {
        row.createCell(0).setCellValue(id);
        row.createCell(1).setCellValue(name);
        row.createCell(2).setCellValue(department);
        row.createCell(3).setCellValue(type);
        row.createCell(4).setCellValue(baseSalary);
        row.createCell(5).setCellValue(bonus);
        row.createCell(6).setCellValue(hourlyRate);
        row.createCell(7).setCellValue(hoursPerMonth);
        row.createCell(8).setCellValue(commissionRate);
        row.createCell(9).setCellValue(monthlySales);
        row.createCell(10).setCellValue(onCallHours);
        row.createCell(11).setCellValue(onCallRate);
    }
}
