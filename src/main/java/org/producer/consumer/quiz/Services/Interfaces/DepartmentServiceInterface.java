package org.producer.consumer.quiz.Services.Interfaces;

import org.producer.consumer.quiz.Request.DepartmentDto;
import org.producer.consumer.quiz.Response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface DepartmentServiceInterface {

    ResponseEntity<ApiResponse> createDepartment(@RequestBody DepartmentDto departmentDto);
}
