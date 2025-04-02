package org.producer.consumer.quiz.Services.Interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserQuestionInterface {

    ResponseEntity<?> getQuestionsByDepartment(@PathVariable Long departmentId);

}
