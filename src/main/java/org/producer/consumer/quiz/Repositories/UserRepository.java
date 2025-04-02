package org.producer.consumer.quiz.Repositories;

import org.producer.consumer.quiz.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String username);
    Optional<User> findById(Long id);
}