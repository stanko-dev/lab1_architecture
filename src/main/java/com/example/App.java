// File: com/example/App.java
package com.example;

import com.example.config.ExcelDatabaseConfig;
import com.example.config.ExcelFileManager;
import com.example.config.H2ConnectionManager;
import com.example.config.MongoConnectionManager;
import com.example.config.MongoDatabaseConfig;
import com.example.config.SqlDatabaseConfig;
import com.example.factory.EmployeeFactory;
import com.example.factory.ManagerFactory;
import com.example.factory.OfficeClerkFactory;
import com.example.factory.SalesManagerFactory;
import com.example.factory.SysAdminFactory;
import com.example.model.Employee;
import com.example.repository.ExcelEmployeeRepository;
import com.example.repository.MongoEmployeeRepository;
import com.example.repository.SqlEmployeeRepository;
import com.example.service.EmployeeService;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class App {
    private static final String DATABASE_DEPARTMENT = "IT";

    public static void main(String[] args) {
        try {
            Map<String, EmployeeFactory> employeeFactories = createEmployeeFactories();

            SqlDatabaseConfig sqlConfig = new SqlDatabaseConfig.Builder()
                    .jdbcUrl("jdbc:h2:file:./employeesdb")
                    .username("sa")
                    .password("")
                    .build();

            MongoDatabaseConfig mongoConfig = new MongoDatabaseConfig.Builder()
                    .connectionString("mongodb://localhost:27017")
                    .databaseName("employeesDB")
                    .collectionName("employees")
                    .build();

            ExcelDatabaseConfig excelConfig = new ExcelDatabaseConfig.Builder()
                    .filePath(Path.of("employees.xlsx"))
                    .sheetName("employees")
                    .build();

            H2ConnectionManager h2ConnectionManager = H2ConnectionManager.getInstance(sqlConfig);
            MongoConnectionManager mongoConnectionManager = MongoConnectionManager.getInstance(mongoConfig);
            ExcelFileManager excelFileManager = ExcelFileManager.getInstance(excelConfig);

            SqlEmployeeRepository sqlRepository = new SqlEmployeeRepository(h2ConnectionManager, employeeFactories);
            MongoEmployeeRepository mongoRepository = new MongoEmployeeRepository(mongoConnectionManager, employeeFactories);
            ExcelEmployeeRepository excelRepository = new ExcelEmployeeRepository(excelFileManager, employeeFactories);

            EmployeeService employeeService = new EmployeeService(
                    sqlRepository,
                    mongoRepository,
                    excelRepository,
                    DATABASE_DEPARTMENT
            );

            printSourcePreview("SQL repository department " + DATABASE_DEPARTMENT, sqlRepository.getEmployeesByDepartment(DATABASE_DEPARTMENT));
            printSourcePreview("Mongo repository", mongoRepository.getAllEmployees());
            printSourcePreview("Excel repository", excelRepository.getAllEmployees());

            List<Employee> combinedEmployees = employeeService.getAllEmployees();
            double averageSalary = employeeService.calculateAverageSalary();
            Map<String, Long> byDepartment = employeeService.countByDepartment();

            System.out.println("Combined employees from SQL, MongoDB, and Excel:");
            combinedEmployees.forEach(employee ->
                    System.out.printf(
                            "  [%s] %s - %s : %.2f%n",
                            employee.getClass().getSimpleName(),
                            employee.getName(),
                            employee.getDepartment(),
                            employee.compensation()
                    )
            );

            System.out.printf("%nAverage compensation: %.2f%n", averageSalary);
            System.out.println("Employees by department:");
            byDepartment.forEach((department, count) ->
                    System.out.printf("  %s -> %d%n", department, count));
        } catch (Exception exception) {
            System.err.println("Application failed: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    private static Map<String, EmployeeFactory> createEmployeeFactories() {
        return Map.of(
                "MANAGER", new ManagerFactory(),
                "OFFICE_CLERK", new OfficeClerkFactory(),
                "SALES_MANAGER", new SalesManagerFactory(),
                "SYS_ADMIN", new SysAdminFactory()
        );
    }

    private static void printSourcePreview(String sourceName, List<Employee> employees) {
        System.out.println(sourceName + " returned " + employees.size() + " employee(s).");
    }
}
