package ra.edu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.edu.entity.Employee;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    @GetMapping
    public ResponseEntity<?> getEmployees() {

        List<Employee> employees = List.of(
                new Employee(1L, "Nguyễn Văn A", 15000000),
                new Employee(2L, "Trần Thị B", 18000000),
                new Employee(3L, "Lê Văn C", 20000000)
        );

        return ResponseEntity.ok(employees);
    }

}
