package com.example.savings.controller;

import com.example.savings.model.Savings;
import com.example.savings.service.SavingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// GitHub Repository Link: https://github.com/ea820/savings

@Controller
@RequestMapping("/savings")
public class SavingsController {

    private static final Logger logger = Logger.getLogger(SavingsController.class.getName());

    @Autowired
    private SavingsService savingsService;

    @GetMapping
    public String getAllSavings(Model model) {
        List<Savings> savings = savingsService.findAll();
        model.addAttribute("savings", savings);
        return "savings-list";
    }

    @GetMapping("/new")
    public String showNewSavingsForm(Model model) {
        Savings savings = new Savings();
        model.addAttribute("savings", savings);
        return "savings-form";
    }

    @PostMapping
    public String createSavings(@ModelAttribute("savings") Savings savings, Model model) {
        if (savingsService.customerNumberExists(savings.getCustno())) {
            model.addAttribute("errorMessage", "The record you are trying to add is already existing. Choose a different customer number.");
            return "savings-form";
        }
        try {
            savingsService.save(savings);
            return "redirect:/savings";
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error creating savings", e);
            model.addAttribute("errorMessage", "Error creating savings: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditSavingsForm(@PathVariable Long id, Model model) {
        Savings savings = savingsService.findById(id);
        if (savings != null) {
            model.addAttribute("savings", savings);
            return "savings-form";
        } else {
            return "redirect:/savings";
        }
    }

    @PostMapping("/{id}/update")
    public String updateSavings(@PathVariable Long id, @ModelAttribute("savings") Savings savings, Model model) {
        try {
            Savings existingSavings = savingsService.findById(id);
            if (existingSavings != null) {
                existingSavings.setCustno(savings.getCustno());
                existingSavings.setCustname(savings.getCustname());
                existingSavings.setCdep(savings.getCdep());
                existingSavings.setNyears(savings.getNyears());
                existingSavings.setSavtype(savings.getSavtype());
                savingsService.save(existingSavings);
            }
            return "redirect:/savings";
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating savings", e);
            model.addAttribute("errorMessage", "Error updating savings: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteSavings(@PathVariable Long id, Model model) {
        try {
            savingsService.deleteById(id);
            return "redirect:/savings";
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting savings", e);
            model.addAttribute("errorMessage", "Error deleting savings: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/{id}/projectedInvestment")
    public String getProjectedInvestment(@PathVariable Long id, Model model) {
        Savings savings = savingsService.findById(id);
        if (savings != null) {
            List<Projection> projections = calculateProjections(savings);
            model.addAttribute("projections", projections);
            model.addAttribute("savings", savings);
        }
        return "savings-projection";
    }

    private List<Projection> calculateProjections(Savings savings) {
        List<Projection> projections = new ArrayList<>();
        double principal = savings.getCdep();
        double rate = savings.getSavtype().equals("Savings-Deluxe") ? 15 : 10;

        for (int year = 1; year <= savings.getNyears(); year++) {
            double interest = principal * rate / 100;
            double endingBalance = principal + interest;
            projections.add(new Projection(year, principal, interest, endingBalance));
            principal = endingBalance;
        }

        return projections;
    }

    public static class Projection {
        private int year;
        private double startingAmount;
        private double interest;
        private double endingBalance;

        public Projection(int year, double startingAmount, double interest, double endingBalance) {
            this.year = year;
            this.startingAmount = startingAmount;
            this.interest = interest;
            this.endingBalance = endingBalance;
        }

        // Getters and Setters
        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public double getStartingAmount() {
            return startingAmount;
        }

        public void setStartingAmount(double startingAmount) {
            this.startingAmount = startingAmount;
        }

        public double getInterest() {
            return interest;
        }

        public void setInterest(double interest) {
            this.interest = interest;
        }

        public double getEndingBalance() {
            return endingBalance;
        }

        public void setEndingBalance(double endingBalance) {
            this.endingBalance = endingBalance;
        }
    }
}
