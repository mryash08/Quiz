package org.producer.consumer.quiz.Services;


import org.producer.consumer.quiz.Model.Answer;
import org.producer.consumer.quiz.Model.Question;
import org.producer.consumer.quiz.Repositories.AnswerRepository;
import org.producer.consumer.quiz.Repositories.QuestionRepository;
import org.producer.consumer.quiz.Repositories.UserRepository;
import org.producer.consumer.quiz.Response.ApiResponse;
import org.producer.consumer.quiz.Response.ResultResponse;
import org.producer.consumer.quiz.Services.Interfaces.ResultServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class ResultService implements ResultServiceInterface {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public ResultService(AnswerRepository answerRepository, QuestionRepository questionRepository, UserRepository userRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }
    public ResponseEntity<?> viewResults(@PathVariable Long userId) {
        List<Answer> answers = answerRepository.findByUserId(userId);
        if(answers.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(HttpStatus.NOT_FOUND.value(), "No results found for user ID: " + userId ));
        }
        int correctAnswers = 0;
        for (Answer answer : answers) {
            Question question = questionRepository.findById(answer.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));
            if (answer.getSelectedOption().equals(question.getCorrectAnswer())) {
                correctAnswers++;
            }
        }

        Long departmentId = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getDepartment()
                .getId();

        int totalDepartmentQuestions = questionRepository.countByDepartmentId(departmentId);

        double percentage = ((double) correctAnswers / totalDepartmentQuestions) * 100;

        return ResponseEntity.ok(new ResultResponse("correctAnswers", "You submitted correct " + correctAnswers + " out of " + totalDepartmentQuestions, percentage));
    }

}
