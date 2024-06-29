package com.geektrust.backend.services;

import com.geektrust.backend.dtos.StationSummaryDto;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.entities.Station;
import com.geektrust.backend.entities.TravelCharges;
import com.geektrust.backend.exceptions.MetroCardAlreadyExistException;
import com.geektrust.backend.exceptions.MetroCardNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class StationService implements IStationService {

    private final HashMap<Station, StationSummaryDto> stationSummaryHashMap;
    private final HashMap<String, MetroCard> metroCards;
    private static final long NO_SERVICE_CHARGE = 0;
    private static final long NO_DISCOUNT_AMOUNT =0;


    public StationService() {
        stationSummaryHashMap = new HashMap<>();
        stationSummaryHashMap.put(Station.CENTRAL, new StationSummaryDto(Station.CENTRAL));
        stationSummaryHashMap.put(Station.AIRPORT, new StationSummaryDto(Station.AIRPORT));
        metroCards = new HashMap<>();
    }


    @Override
    public void metroCardBalance(String metroCardNumber, long balance) throws ArithmeticException, MetroCardAlreadyExistException {
        MetroCard metroCard = new MetroCard(metroCardNumber, balance);
        addMetroCardBalanceDetail(metroCardNumber, metroCard);
    }

    private void addMetroCardBalanceDetail(String metroCardNumber, MetroCard metroCard) throws MetroCardAlreadyExistException {
        if (metroCards.containsKey(metroCardNumber)) {
            throw new MetroCardAlreadyExistException("Metro card already exist with same number");
        }
        metroCards.put(metroCardNumber, metroCard);
    }


    @Override
    public void checkInStation(String metroCardNumber, PassengerType passengerType, Station fromStation) throws MetroCardNotFoundException {
        MetroCard metroCard = getMetroCard(metroCardNumber);
        long travelCharges = TravelCharges.getCharges(passengerType);
        metroCard.checkIfReturnJourney(fromStation);
        long discount = calculateDiscount(metroCard,travelCharges);
        travelCharges -= discount;
        long serviceCharge = calculateServiceChargeIfApplicable(metroCard, travelCharges);
        metroCard.updateJourney(travelCharges,fromStation);
        updateMetroCardDetailsMap(metroCard);
        updateStationSummary(fromStation, passengerType, travelCharges + serviceCharge, discount);
    }

    private MetroCard getMetroCard(String metroCardNumber) {
        Optional<MetroCard> metroCard = Optional.ofNullable(metroCards.get(metroCardNumber));
        if(!metroCard.isPresent())
            throw new MetroCardNotFoundException("Metro card does not exist with given number");
        return metroCard.get();
    }

    private void updateStationSummary(Station fromStation, PassengerType passengerType, long totalAmount, long discount) {
        stationSummaryHashMap.put(fromStation, stationSummaryHashMap.get(fromStation)
                .updateStationDto(passengerType, totalAmount, discount));
    }

    private void updateMetroCardDetailsMap(MetroCard metroCard) {
        metroCards.put(metroCard.getCardNumber(), metroCard);
    }

    private long calculateServiceChargeIfApplicable(MetroCard metroCard, long travelCharges) {
        long serviceCharge = NO_SERVICE_CHARGE;
        if (!isBalanceSufficientToTravel(metroCard, travelCharges)) {
            serviceCharge = metroCard.rechargeCard(travelCharges);
        }
        return serviceCharge;
    }

    private long calculateDiscount(MetroCard metroCard, long travelCharges) {
        long discount = NO_DISCOUNT_AMOUNT;
        if (metroCard.isReturnJourney()) {
            discount = TravelCharges.getDiscountedPrice(travelCharges);
        }
        return discount;
    }


    private boolean isBalanceSufficientToTravel(MetroCard metroCard, long travelCharges) {
        return metroCard.getBalance() >= travelCharges;
    }

    @Override
    public List<StationSummaryDto> getStationSummary() {
        ArrayList<StationSummaryDto> stationSummaryList = new ArrayList<>(stationSummaryHashMap.values());
        stationSummaryList.sort((stationDto1,stationDto2) ->
                stationDto2.getStation().toString().compareTo(stationDto1.getStation().toString()));
        return  stationSummaryList;
    }
}
