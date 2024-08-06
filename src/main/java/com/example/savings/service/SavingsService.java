package com.example.savings.service;

import com.example.savings.dao.SavingsDAO;
import com.example.savings.model.Savings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SavingsService {

    @Autowired
    private SavingsDAO savingsDAO;

    public List<Savings> findAll() {
        return savingsDAO.findAll();
    }

    public Savings save(Savings savings) {
        return savingsDAO.save(savings);
    }

    public Savings findById(Long id) {
        Optional<Savings> optionalSavings = savingsDAO.findById(id);
        if (optionalSavings.isPresent()) {
            return optionalSavings.get();
        } else {
            throw new RuntimeException("Savings not found for id :: " + id);
        }
    }

    public void deleteById(Long id) {
        savingsDAO.deleteById(id);
    }

    public boolean customerNumberExists(String custno) {
        return savingsDAO.findAll().stream().anyMatch(s -> s.getCustno().equals(custno));
    }

    public double calculateCompoundInterest(double principal, double rate, int time) {
        return principal * Math.pow(1 + rate / 100, time) - principal;
    }
}
