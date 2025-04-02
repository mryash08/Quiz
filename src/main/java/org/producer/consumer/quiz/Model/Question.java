package org.producer.consumer.quiz.Model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;

    @ElementCollection
    private List<String> options;

    private String correctAnswer;

    @ManyToOne
    @JoinColumn(name = "departmentid")
    private Department department;
}
