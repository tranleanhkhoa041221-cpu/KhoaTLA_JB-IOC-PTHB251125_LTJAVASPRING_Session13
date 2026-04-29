package ra.edu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ra.edu.dto.request.EmployeeCreateDTO;
import ra.edu.entity.Employee;
import ra.edu.repository.EmployeeRepository;
import com.cloudinary.Cloudinary;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final Cloudinary cloudinary;
    private final EmployeeRepository employeeRepository;

    public Employee createEmployee(EmployeeCreateDTO dto) {


        String avatarUrl = uploadToCloudinary(dto.getAvatarFile());


        Employee employee = Employee.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .department(dto.getDepartment())
                .avatarUrl(avatarUrl)
                .build();


        return employeeRepository.save(employee);
    }

    private String uploadToCloudinary(MultipartFile file) {
        try {
            Map<String, Object> params = Map.of(
                    "resource_type", "auto"
            );

            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    params
            );

            return uploadResult.get("secure_url").toString();

        } catch (IOException e) {
            throw new RuntimeException("Upload ảnh thất bại");
        }
    }

}
