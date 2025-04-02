package org.producer.consumer.quiz.Controller;
import org.producer.consumer.quiz.Request.DepartmentDto;
import org.producer.consumer.quiz.Response.ApiResponse;
import org.producer.consumer.quiz.Services.Interfaces.DepartmentServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/department")
@PreAuthorize("hasAuthority('ADMIN')")
public class DepartmentController {
    @Autowired
    private DepartmentServiceInterface departmentServiceInterface;

    @PostMapping
    public ResponseEntity<ApiResponse> createDepartment(@RequestBody DepartmentDto departmentDto) {
        try {
            return departmentServiceInterface.createDepartment(departmentDto);
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(HttpStatus.BAD_REQUEST.value(),"Invalid department data" ));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(HttpStatus.BAD_REQUEST.value(),ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage()));
        }
    }
}
