// File: com/example/repository/ExcelEmployeeRepository.java
package com.example.repository;

import com.example.config.ExcelFileManager;
import com.example.factory.EmployeeFactory;
import com.example.model.Employee;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ExcelEmployeeRepository implements EmployeeRepository {
    private final ExcelFileManager fileManager;
    private final Map<String, EmployeeFactory> employeeFactories;

    public ExcelEmployeeRepository(ExcelFileManager fileManager, Map<String, EmployeeFactory> employeeFactories) {
        this.fileManager = fileManager;
        this.employeeFactories = employeeFactories;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();

        try (XSSFWorkbook workbook = fileManager.openWorkbook()) {
            Sheet sheet = workbook.getSheet(fileManager.getSheetName());
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }

                employees.add(resolveFactory(row.getCell(3).getStringCellValue()).createEmployee(
                        (int) row.getCell(0).getNumericCellValue(),
                        row.getCell(1).getStringCellValue(),
                        row.getCell(2).getStringCellValue(),
                        row.getCell(4).getNumericCellValue(),
                        row.getCell(5).getNumericCellValue(),
                        row.getCell(6).getNumericCellValue(),
                        (int) row.getCell(7).getNumericCellValue(),
                        row.getCell(8).getNumericCellValue(),
                        row.getCell(9).getNumericCellValue(),
                        (int) row.getCell(10).getNumericCellValue(),
                        row.getCell(11).getNumericCellValue()
                ));
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to read employees from Excel", exception);
        }

        return employees;
    }

    private EmployeeFactory resolveFactory(String type) {
        EmployeeFactory factory = employeeFactories.get(type.toUpperCase(Locale.ROOT));
        if (factory == null) {
            throw new IllegalArgumentException("Unsupported employee type: " + type);
        }
        return factory;
    }
}
