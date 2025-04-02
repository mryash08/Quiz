package org.producer.consumer.quiz.Controller;


import org.producer.consumer.quiz.Services.Interfaces.UserQuestionInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/questions")
public class UserQuestionController {

    @Autowired
    private UserQuestionInterface questionInterface;

    @GetMapping("/{departmentId}")
    public ResponseEntity<?> getQuestionsByDepartment(@PathVariable Long departmentId) {
        return questionInterface.getQuestionsByDepartment(departmentId);
    }
}
