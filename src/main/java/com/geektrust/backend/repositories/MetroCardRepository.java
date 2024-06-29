package com.geektrust.backend.repositories;

import com.geektrust.backend.entities.MetroCard;

import java.util.HashMap;
import java.util.Optional;

public class MetroCardRepository {
    private final HashMap<String, MetroCard> metroCardHashMap;

    public MetroCardRepository() {
        this.metroCardHashMap = new HashMap<>();
    }

    public void save(MetroCard metroCard) {
        metroCardHashMap.put(metroCard.getCardNumber(), metroCard);
    }

    public Optional<MetroCard> findByNumber(String cardNumber) {
        return Optional.ofNullable(getMetroCardHashMap().get(cardNumber));
    }

    public boolean isExistByNumber(String cardNumber) {
        return getMetroCardHashMap().containsKey(cardNumber);
    }

    private HashMap<String, MetroCard> getMetroCardHashMap() {
        return metroCardHashMap;
    }
}
