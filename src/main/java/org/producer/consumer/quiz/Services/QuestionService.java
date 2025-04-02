package org.producer.consumer.quiz.Services;

import org.producer.consumer.quiz.Request.QuestionDto;
import org.producer.consumer.quiz.Model.Department;
import org.producer.consumer.quiz.Model.Question;
import org.producer.consumer.quiz.Repositories.DepartmentRepository;
import org.producer.consumer.quiz.Repositories.QuestionRepository;
import org.producer.consumer.quiz.Response.ApiResponse;

import org.producer.consumer.quiz.Services.Interfaces.QuestionServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class QuestionService implements QuestionServiceInterface {

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    QuestionRepository questionRepository;

    @PostMapping
    public ResponseEntity<ApiResponse> addQuestion(@RequestBody QuestionDto questionDto) {
        try {
            Department department = departmentRepository.findById(questionDto.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));

            Question question = new Question();
            question.setQuestionText(questionDto.getQuestion());
            List<String> options = List.of(
                    questionDto.getOptionA(),
                    questionDto.getOptionB(),
                    questionDto.getOptionC(),
                    questionDto.getOptionD()
            );
            question.setOptions(options);
            question.setDepartment(department);
            question.setCorrectAnswer(questionDto.getCorrectOption());

            questionRepository.save(question);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.CREATED.value(),"Question added successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Error adding question: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateQuestion(@PathVariable Long id, @RequestBody QuestionDto questionDto) {

        try {
            Optional<Question> optionalQuestion = questionRepository.findById(id);
            if (optionalQuestion.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(HttpStatus.NOT_FOUND.value(), "Question not found"));

            Department department = departmentRepository.findById(questionDto.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            Question question = optionalQuestion.get();
            List<String> options = new ArrayList<>();
            question.setQuestionText(questionDto.getQuestion());
            options.add(questionDto.getOptionA());
            options.add(questionDto.getOptionB());
            options.add(questionDto.getOptionC());
            options.add(questionDto.getCorrectOption());
            question.setOptions(options);
            question.setDepartment(department);
            question.setCorrectAnswer(questionDto.getCorrectOption());

            questionRepository.save(question);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Question updated successfully!"));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Error updating question: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteQuestion(@PathVariable Long id) {
        try {
            if (!questionRepository.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(HttpStatus.NOT_FOUND.value(), "Question not found"));
            }
            questionRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Question deleted successfully!"));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Error deleting question: " + e.getMessage()));
        }
    }
}
