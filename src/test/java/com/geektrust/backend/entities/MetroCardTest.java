package com.geektrust.backend.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MetroCardTest {

    @Test
    void shouldThrowArithmeticExceptionWhenNegativeBalancePassed() {
        Assertions.assertThrows(ArithmeticException.class, () -> {
            new MetroCard("1234", -12L);
        });
    }

    @Test
    void rechargeCard() {
        MetroCard metroCard = new MetroCard("1234", 100L);
        long serviceCharge = metroCard.rechargeCard(200L);
        Assertions.assertEquals(2, serviceCharge);
        Assertions.assertEquals(200, metroCard.getBalance());
    }

    @Test
    void checkIfReturnJourney() {
        MetroCard metroCard = new MetroCard("1234", 100L);
        Assertions.assertFalse(metroCard.isReturnJourney());
        metroCard.updateJourney(50,Station.AIRPORT);
        metroCard.checkIfReturnJourney(Station.CENTRAL);
        Assertions.assertTrue(metroCard.isReturnJourney());
    }

    @Test
    void updateJourney() {
        MetroCard metroCard = new MetroCard("1234", 100L);
        metroCard.updateJourney(50L,Station.CENTRAL);
        Assertions.assertEquals(50,metroCard.getBalance());
    }
}