package com.smailelfathi.consumeservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smailelfathi.consumeservice.model.Quote;

public interface QuotesRepository extends JpaRepository<Quote, Integer> {
    List<Quote> findByUserName(String username);
}
