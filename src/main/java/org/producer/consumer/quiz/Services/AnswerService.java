package org.producer.consumer.quiz.Services;

import org.producer.consumer.quiz.Model.Question;
import org.producer.consumer.quiz.Repositories.QuestionRepository;
import org.producer.consumer.quiz.Request.AnswerDto;
import org.producer.consumer.quiz.Model.Answer;
import org.producer.consumer.quiz.Model.User;
import org.producer.consumer.quiz.Repositories.AnswerRepository;
import org.producer.consumer.quiz.Repositories.UserRepository;
import org.producer.consumer.quiz.Services.Interfaces.AnswerServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class AnswerService implements AnswerServiceInterface {

    private final AnswerRepository answerRepository;

    private final UserRepository userRepository;

    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, UserRepository userRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    public String submitAnswers(@RequestBody AnswerDto answerDto) {

        if(answerDto == null || answerDto.getAnswers() == null || answerDto.getAnswers().isEmpty()){
            throw new RuntimeException("Answer submission cannot be empty.");
        }
        if(answerRepository.existsByUserId(answerDto.getUserId())){
            throw new RuntimeException("User with ID " + answerDto.getUserId() + " Already Submitted.");
        }
        for (AnswerDto.AnswerItem item : answerDto.getAnswers()) {
            Answer answer = new Answer();
            answer.setUserId(answerDto.getUserId());
            answer.setQuestionId(item.getQuestionId());
            answer.setSelectedOption(item.getSelectedOption());

            User user = userRepository.findById(answerDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
            Question question = questionRepository.findById(item.getQuestionId()).orElseThrow(() -> new RuntimeException("Question not found"));

            if(!question.getDepartment().getId().equals(user.getDepartment().getId())){
                throw new RuntimeException("Question does not belong to user's department");
            }
            answerRepository.save(answer);
        }
        return "Answers submitted successfully!";
    }

    @Override
    public boolean isUserExists(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user != null;
    }

    @Transactional
    @Override
    public void resetAnswers(Long userId) {

        try {
            answerRepository.deleteAllByUserId(userId);
        }catch (Exception e){
            throw new RuntimeException("Error while resetting answers");
        }
    }
}
