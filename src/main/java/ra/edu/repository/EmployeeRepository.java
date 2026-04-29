package ra.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.edu.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
