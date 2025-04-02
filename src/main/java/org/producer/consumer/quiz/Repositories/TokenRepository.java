package org.producer.consumer.quiz.Repositories;

import org.producer.consumer.quiz.Model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.stereotype.Repository;
@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    Optional<Token> findByToken(String token);
}