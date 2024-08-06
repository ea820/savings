package com.example.savings.dao.impl;

import com.example.savings.dao.SavingsDAO;
import com.example.savings.model.Savings;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class SavingsDAOImpl implements SavingsDAO {

    private final List<Savings> savingsList = new ArrayList<>();

    @Override
    public List<Savings> findAll() {
        return new ArrayList<>(savingsList);
    }

    @Override
    public Optional<Savings> findById(Long id) {
        return savingsList.stream()
                .filter(savings -> savings.getId().equals(id))
                .findFirst();
    }

    @Override
    public Savings save(Savings savings) {
        savingsList.removeIf(existingSavings -> existingSavings.getId().equals(savings.getId()));
        savingsList.add(savings);
        return savings;
    }

    @Override
    public void deleteById(Long id) {
        savingsList.removeIf(savings -> savings.getId().equals(id));
    }
}
