package com.faq.faqbackend.controller;

import com.faq.faqbackend.exception.ResourceNotFoundException;
import com.faq.faqbackend.model.Question;
import com.faq.faqbackend.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionControllerTest {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private QuestionController questionController;

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

        when(questionService.createQuestion(newQuestion)).thenReturn(newQuestion);

        ResponseEntity<String> responseEntity = questionController.createQuestion(newQuestion);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains("Question created successfully"));

        verify(questionService, times(1)).createQuestion(newQuestion);
    }

    @Test
    void testGetAllQuestions() {
        when(questionService.getAllQuestions()).thenReturn(Collections.emptyList());

        List<Question> questions = questionController.getAllQuestions();

        assertNotNull(questions);
        assertEquals(0, questions.size());

        verify(questionService, times(1)).getAllQuestions();
    }

    @Test
    void testGetQuestionById() {
        Long questionId = 1L;
        Question question = new Question();
        question.setId(questionId);

        when(questionService.getQuestionById(questionId)).thenReturn(question);

        ResponseEntity<?> responseEntity = questionController.getQuestionById(questionId);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(question, responseEntity.getBody());

        verify(questionService, times(1)).getQuestionById(questionId);
    }

    @Test
    void testGetQuestionByIdNotFound() {
        Long questionId = 1L;
        when(questionService.getQuestionById(questionId)).thenThrow(new ResourceNotFoundException("Question not found"));

        ResponseEntity<?> responseEntity = questionController.getQuestionById(questionId);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().toString().contains("Resource not found"));

        verify(questionService, times(1)).getQuestionById(questionId);
    }

    @Test
    void testUpdateQuestion() {
        Long questionId = 1L;
        Question updatedQuestion = new Question();
        updatedQuestion.setId(questionId);
        updatedQuestion.setQuestion("Updated Question");
        updatedQuestion.setCategory("Updated Category");
        updatedQuestion.setState("Updated State");

        when(questionService.updateQuestion(questionId, updatedQuestion)).thenReturn(updatedQuestion);

        ResponseEntity<String> responseEntity = questionController.updateQuestion(questionId, updatedQuestion);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains("Question updated successfully"));

        verify(questionService, times(1)).updateQuestion(questionId, updatedQuestion);
    }

    @Test
    void testUpdateQuestionNotFound() {
        Long questionId = 1L;
        Question updatedQuestion = new Question();
        updatedQuestion.setId(questionId);

        when(questionService.updateQuestion(questionId, updatedQuestion)).thenThrow(new ResourceNotFoundException("Question not found"));

        ResponseEntity<String> responseEntity = questionController.updateQuestion(questionId, updatedQuestion);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains("Resource not found"));

        verify(questionService, times(1)).updateQuestion(questionId, updatedQuestion);
    }

    @Test
    void testDeleteQuestion() {
        Long questionId = 1L;

        ResponseEntity<String> responseEntity = questionController.deleteQuestion(questionId);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains("Question deleted successfully"));

        verify(questionService, times(1)).deleteQuestion(questionId);
    }

    @Test
    void testDeleteQuestionNotFound() {
        Long questionId = 1L;
        doThrow(new ResourceNotFoundException("Question not found")).when(questionService).deleteQuestion(questionId);

        ResponseEntity<String> responseEntity = questionController.deleteQuestion(questionId);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains("Resource not found"));

        verify(questionService, times(1)).deleteQuestion(questionId);
    }
}
