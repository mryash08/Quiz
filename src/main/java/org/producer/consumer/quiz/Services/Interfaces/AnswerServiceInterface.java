package org.producer.consumer.quiz.Services.Interfaces;

import org.producer.consumer.quiz.Request.AnswerDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

public interface AnswerServiceInterface {

    String submitAnswers(@RequestBody AnswerDto answerDto);


    boolean isUserExists(Long userId);


    @Transactional
    void resetAnswers(Long userId);
}
