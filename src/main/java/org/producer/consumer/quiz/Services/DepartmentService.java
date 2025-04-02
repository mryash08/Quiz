package org.producer.consumer.quiz.Services;


import org.producer.consumer.quiz.Model.Department;
import org.producer.consumer.quiz.Repositories.DepartmentRepository;
import org.producer.consumer.quiz.Request.DepartmentDto;
import org.producer.consumer.quiz.Response.ApiResponse;
import org.producer.consumer.quiz.Services.Interfaces.DepartmentServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class DepartmentService implements DepartmentServiceInterface {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public ResponseEntity<ApiResponse> createDepartment(@RequestBody DepartmentDto departmentdto) {


        if (departmentRepository.existsByName(departmentdto.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse(HttpStatus.CONFLICT.value(),"Department with this name already exists!"));
        }

        Department department = new Department();
        department.setName(departmentdto.getName());
        departmentRepository.save(department);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(HttpStatus.CREATED.value(),"Department created successfully!"));
    }
}
