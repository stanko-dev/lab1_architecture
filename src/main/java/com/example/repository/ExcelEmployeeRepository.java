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

public class ExcelEmployeeRepository implements EmployeeRepository {
    private final ExcelFileManager fileManager;
    private final EmployeeFactory employeeFactory;

    public ExcelEmployeeRepository(ExcelFileManager fileManager, EmployeeFactory employeeFactory) {
        this.fileManager = fileManager;
        this.employeeFactory = employeeFactory;
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

                employees.add(employeeFactory.createEmployee(
                        row.getCell(3).getStringCellValue(),
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
}
