package org.producer.consumer.quiz.Repositories;

import org.producer.consumer.quiz.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByDepartmentId(Long departmentId);

    int countByDepartmentId(Long departmentId);
}
