package com.geektrust.backend.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MetroCardTest {
    private static final String TEST_CARD_NUMBER = "1234";

    @Test
    void shouldThrowArithmeticExceptionWhenNegativeBalancePassed() {
        Assertions.assertThrows(ArithmeticException.class, () -> {
            new MetroCard(TEST_CARD_NUMBER, -12L);
        });
    }

    @Test
    void rechargeCard() {
        MetroCard metroCard = new MetroCard(TEST_CARD_NUMBER, 100L);
        long serviceCharge = metroCard.rechargeCard(200L);
        Assertions.assertEquals(2, serviceCharge);
        Assertions.assertEquals(200, metroCard.getBalance());
    }

    @Test
    void checkIfReturnJourney() {
        MetroCard metroCard = new MetroCard(TEST_CARD_NUMBER, 100L);
        Assertions.assertFalse(metroCard.isReturnJourney());
        metroCard.updateJourney(50,Station.AIRPORT);
        metroCard.checkIfReturnJourney(Station.CENTRAL);
        Assertions.assertTrue(metroCard.isReturnJourney());
    }

    @Test
    void updateJourney() {
        MetroCard metroCard = new MetroCard(TEST_CARD_NUMBER, 100L);
        metroCard.updateJourney(50L,Station.CENTRAL);
        Assertions.assertEquals(50,metroCard.getBalance());
    }
}