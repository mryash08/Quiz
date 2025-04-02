package org.producer.consumer.quiz.Repositories;

import org.producer.consumer.quiz.Model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    boolean existsByName(String name);

}
