package com.faq.faqbackend.user;

import com.faq.faqbackend.model.Question;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class QuestionTest {

    @Test
    public void testGettersAndSetters() {
        Question question = new Question();

        question.setId(1L);
        question.setQuestion("What is your question?");
        question.setCategory("Category");
        question.setState("State");


        assertEquals(1L, question.getId());
        assertEquals("What is your question?", question.getQuestion());
        assertEquals("Category", question.getCategory());
        assertEquals("State", question.getState());
    }

    @Test
    public void testConstructor() {

        Question question = new Question(1L, "What is your question?", "Category", "State");


        assertEquals(1L, question.getId());
        assertEquals("What is your question?", question.getQuestion());
        assertEquals("Category", question.getCategory());
        assertEquals("State", question.getState());
    }

    @Test
    public void testDefaultConstructor() {

        Question question = new Question();


        assertNull(question.getId());
        assertNull(question.getQuestion());
        assertNull(question.getCategory());
        assertNull(question.getState());
    }
}
