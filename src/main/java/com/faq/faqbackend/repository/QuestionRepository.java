package com.faq.faqbackend.repository;

import com.faq.faqbackend.model.Question;
import com.faq.faqbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
