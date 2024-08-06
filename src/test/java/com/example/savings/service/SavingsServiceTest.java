package com.example.savings.service;

import com.example.savings.dao.SavingsDAO;
import com.example.savings.model.Savings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SavingsServiceTest {

    @Mock
    private SavingsDAO savingsDAO;

    @InjectMocks
    private SavingsService savingsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        Savings savings1 = new Savings();
        savings1.setCustno("115");
        savings1.setCustname("Jasper Diaz");

        Savings savings2 = new Savings();
        savings2.setCustno("112");
        savings2.setCustname("Zanip Mendez");

        List<Savings> savingsList = Arrays.asList(savings1, savings2);

        when(savingsDAO.findAll()).thenReturn(savingsList);

        List<Savings> result = savingsService.findAll();
        assertEquals(2, result.size());
        verify(savingsDAO, times(1)).findAll();
    }

    @Test
    void findById() {
        Savings savings = new Savings();
        savings.setCustno("115");
        savings.setCustname("Jasper Diaz");

        when(savingsDAO.findById(1L)).thenReturn(Optional.of(savings));

        Savings result = savingsService.findById(1L);
        assertNotNull(result);
        assertEquals("Jasper Diaz", result.getCustname());
        verify(savingsDAO, times(1)).findById(1L);
    }

    @Test
    void save() {
        Savings savings = new Savings();
        savings.setCustno("115");
        savings.setCustname("Jasper Diaz");

        when(savingsDAO.save(savings)).thenReturn(savings);

        Savings result = savingsService.save(savings);
        assertNotNull(result);
        assertEquals("Jasper Diaz", result.getCustname());
        verify(savingsDAO, times(1)).save(savings);
    }

    @Test
    void deleteById() {
        doNothing().when(savingsDAO).deleteById(1L);

        savingsService.deleteById(1L);
        verify(savingsDAO, times(1)).deleteById(1L);
    }

    @Test
    void customerNumberExists() {
        Savings savings = new Savings();
        savings.setCustno("115");

        when(savingsDAO.findAll()).thenReturn(Arrays.asList(savings));

        boolean result = savingsService.customerNumberExists("115");
        assertTrue(result);
        verify(savingsDAO, times(1)).findAll();
    }
}
