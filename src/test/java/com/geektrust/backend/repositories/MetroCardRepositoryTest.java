package com.geektrust.backend.repositories;

import com.geektrust.backend.entities.MetroCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

public class MetroCardRepositoryTest {

    private MetroCardRepository metroCardRepository;

    @BeforeEach
    public void setUp() {
        metroCardRepository = new MetroCardRepository();
    }

    @Test
    public void testSave() {
        MetroCard metroCard = new MetroCard("12345", 100);
        metroCardRepository.save(metroCard);

        Optional<MetroCard> result = metroCardRepository.findByNumber("12345");
        assertTrue(result.isPresent());
        assertEquals("12345", result.get().getCardNumber());
    }

    @Test
    public void testFindByNumber_CardExists() {
        MetroCard metroCard = new MetroCard("12345", 100);
        metroCardRepository.save(metroCard);

        Optional<MetroCard> result = metroCardRepository.findByNumber("12345");
        assertTrue(result.isPresent());
        assertEquals("12345", result.get().getCardNumber());
    }

    @Test
    public void testFindByNumber_CardDoesNotExist() {
        Optional<MetroCard> result = metroCardRepository.findByNumber("54321");
        assertFalse(result.isPresent());
    }

    @Test
    public void testIsExistByNumber_CardExists() {
        MetroCard metroCard = new MetroCard("12345", 100);
        metroCardRepository.save(metroCard);

        assertTrue(metroCardRepository.isExistByNumber("12345"));
    }

    @Test
    public void testIsExistByNumber_CardDoesNotExist() {
        assertFalse(metroCardRepository.isExistByNumber("54321"));
    }
}
