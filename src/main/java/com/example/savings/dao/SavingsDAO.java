package com.example.savings.dao;

import com.example.savings.model.Savings;
import java.util.List;
import java.util.Optional;

public interface SavingsDAO {
    List<Savings> findAll();
    Optional<Savings> findById(Long id);
    Savings save(Savings savings);
    void deleteById(Long id);
}
