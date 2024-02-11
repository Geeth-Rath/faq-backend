package com.faq.faqbackend.service;
import com.faq.faqbackend.exception.ResourceNotFoundException;
import com.faq.faqbackend.model.Question;
import com.faq.faqbackend.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question createQuestion(Question newQuestion) {
        // Implement any validation or business logic here
        return questionRepository.save(newQuestion);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));
    }

    public Question updateQuestion(Long id, Question updatedQuestion) {
        Question question = getQuestionById(id);
        question.setQuestion(updatedQuestion.getQuestion());
        question.setCategory(updatedQuestion.getCategory());
        question.setState(updatedQuestion.getState());
        return questionRepository.save(question);
    }

    public void deleteQuestion(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));
        questionRepository.delete(question);
    }


}
