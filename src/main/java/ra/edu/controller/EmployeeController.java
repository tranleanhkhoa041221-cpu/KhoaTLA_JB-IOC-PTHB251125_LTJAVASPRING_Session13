package ra.edu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.edu.dto.request.EmployeeCreateDTO;
import ra.edu.entity.Employee;
import ra.edu.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<?> createEmployee(@ModelAttribute EmployeeCreateDTO dto) {

        Employee employee = employeeService.createEmployee(dto);

        return ResponseEntity.status(201).body(employee);
    }
}
//    @GetMapping
//    public ResponseEntity<?> getEmployees() {
//
//        List<Employee> employees = List.of(
//                new Employee(1L, "Nguyễn Văn A", 15000000),
//                new Employee(2L, "Trần Thị B", 18000000),
//                new Employee(3L, "Lê Văn C", 20000000)
//        );
//
//        return ResponseEntity.ok(employees);
//    }
//
//}
