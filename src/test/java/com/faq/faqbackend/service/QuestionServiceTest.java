package com.faq.faqbackend.service;

import com.faq.faqbackend.exception.ResourceNotFoundException;
import com.faq.faqbackend.model.Question;
import com.faq.faqbackend.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateQuestion() {
        Question newQuestion = new Question();
        newQuestion.setQuestion("What is your question?");
        newQuestion.setCategory("Category");
        newQuestion.setState("State");

        when(questionRepository.save(newQuestion)).thenReturn(newQuestion);

        Question savedQuestion = questionService.createQuestion(newQuestion);

        assertNotNull(savedQuestion);
        assertEquals("What is your question?", savedQuestion.getQuestion());
        assertEquals("Category", savedQuestion.getCategory());
        assertEquals("State", savedQuestion.getState());

        verify(questionRepository, times(1)).save(newQuestion);
    }

    @Test
    void testGetAllQuestions() {
        when(questionRepository.findAll()).thenReturn(Collections.emptyList());

        List<Question> questions = questionService.getAllQuestions();

        assertNotNull(questions);
        assertEquals(0, questions.size());

        verify(questionRepository, times(1)).findAll();
    }

    @Test
    void testGetQuestionById() {
        Long questionId = 1L;
        Question question = new Question();
        question.setId(questionId);
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));

        Question retrievedQuestion = questionService.getQuestionById(questionId);

        assertNotNull(retrievedQuestion);
        assertEquals(questionId, retrievedQuestion.getId());

        verify(questionRepository, times(1)).findById(questionId);
    }

    @Test
    void testUpdateQuestion() {
        Long questionId = 1L;
        Question existingQuestion = new Question();
        existingQuestion.setId(questionId);
        existingQuestion.setQuestion("Old Question");
        existingQuestion.setCategory("Old Category");
        existingQuestion.setState("Old State");

        Question updatedQuestion = new Question();
        updatedQuestion.setId(questionId);
        updatedQuestion.setQuestion("Updated Question");
        updatedQuestion.setCategory("Updated Category");
        updatedQuestion.setState("Updated State");

        when(questionRepository.findById(questionId)).thenReturn(Optional.of(existingQuestion));
        when(questionRepository.save(existingQuestion)).thenReturn(updatedQuestion);

        Question savedQuestion = questionService.updateQuestion(questionId, updatedQuestion);

        assertNotNull(savedQuestion);
        assertEquals(questionId, savedQuestion.getId());
        assertEquals("Updated Question", savedQuestion.getQuestion());
        assertEquals("Updated Category", savedQuestion.getCategory());
        assertEquals("Updated State", savedQuestion.getState());

        verify(questionRepository, times(1)).findById(questionId);
        verify(questionRepository, times(1)).save(existingQuestion);
    }

    @Test
    void testDeleteQuestion() {
        Long questionId = 1L;
        Question question = new Question();
        question.setId(questionId);
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));

        questionService.deleteQuestion(questionId);

        verify(questionRepository, times(1)).delete(question);
    }

    @Test
    void testDeleteQuestionNotFound() {
        Long questionId = 1L;
        when(questionRepository.findById(questionId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> questionService.deleteQuestion(questionId));
    }
}
