package org.producer.consumer.quiz.Repositories;


import org.producer.consumer.quiz.Model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {


    List<Answer> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    void deleteAllByUserId(Long userId);
}
