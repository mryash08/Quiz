package org.producer.consumer.quiz.Services;

import org.producer.consumer.quiz.Exception.ResourceNotFoundException;
import org.producer.consumer.quiz.Model.Question;
import org.producer.consumer.quiz.Repositories.DepartmentRepository;
import org.producer.consumer.quiz.Repositories.QuestionRepository;
import org.producer.consumer.quiz.Response.ApiResponse;
import org.producer.consumer.quiz.Response.QuestionResponse;
import org.producer.consumer.quiz.Services.Interfaces.UserQuestionInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserQuestionService implements UserQuestionInterface {

    private final QuestionRepository questionRepository;
    private final DepartmentRepository departmentRepository;

    public UserQuestionService(QuestionRepository questionRepository, DepartmentRepository departmentRepository) {
        this.questionRepository = questionRepository;
        this.departmentRepository = departmentRepository;
    }

    public ResponseEntity<?> getQuestionsByDepartment(@PathVariable Long departmentId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new ResourceNotFoundException("Department not found with ID: " + departmentId);
        }
        List<Question> questions = questionRepository.findAllByDepartmentId(departmentId);
        if (questions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(HttpStatus.NOT_FOUND.value(), "No questions found for this department"));
        }
        List<QuestionResponse> responseDTOs = new ArrayList<>();
        for (Question question : questions) {
            responseDTOs.add(new QuestionResponse(question.getId(),question.getDepartment().getId(), question.getQuestionText(), question.getOptions()));
        }
        return ResponseEntity.ok(responseDTOs);
    }
}
