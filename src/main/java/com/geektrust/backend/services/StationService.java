package com.geektrust.backend.services;

import com.geektrust.backend.dtos.StationSummaryDto;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.entities.Station;
import com.geektrust.backend.entities.TravelCharges;
import com.geektrust.backend.exceptions.MetroCardAlreadyExistException;
import com.geektrust.backend.exceptions.MetroCardNotFoundException;
import com.geektrust.backend.repositories.MetroCardRepository;
import com.geektrust.backend.repositories.StationSummaryRepository;

import java.util.List;
import java.util.Optional;

public class StationService implements IStationService {
    private final MetroCardRepository metroCardRepository;
    private final StationSummaryRepository stationSummaryRepository;
    private static final String EXIST_WITH_SAME_NUMBER_ERROR = "Metro card already exist with same number";
    private static final String DOES_NOT_EXIST_WITH_GIVEN_NUMBER_ERROR = "Metro card does not exist with given number";
    private static final long NO_SERVICE_CHARGE = 0;
    private static final long NO_DISCOUNT_AMOUNT = 0;

    public StationService(MetroCardRepository metroCardRepository, StationSummaryRepository stationSummaryRepository) {
        this.metroCardRepository = metroCardRepository;
        this.stationSummaryRepository = stationSummaryRepository;
    }
    @Override
    public void metroCardBalance(String metroCardNumber, long balance) throws ArithmeticException, MetroCardAlreadyExistException {
        if(metroCardRepository.isExistByNumber(metroCardNumber))
            throw new MetroCardAlreadyExistException(EXIST_WITH_SAME_NUMBER_ERROR);
        MetroCard metroCard = new MetroCard(metroCardNumber, balance);
        metroCardRepository.save(metroCard);
    }
    @Override
    public void checkInStation(String metroCardNumber, PassengerType passengerType, Station fromStation) throws MetroCardNotFoundException {
        MetroCard metroCard = getMetroCard(metroCardNumber);
        long travelCharges = TravelCharges.getCharges(passengerType);
        long discount = getDiscount(fromStation, metroCard, travelCharges);
        travelCharges -= discount;
        long serviceCharge = calculateServiceChargeIfApplicable(metroCard, travelCharges);
        metroCard.updateJourney(travelCharges, fromStation);
        metroCardRepository.save(metroCard);
        stationSummaryRepository.updateStationSummaryDto(fromStation, passengerType, travelCharges + serviceCharge, discount);
    }
    private long getDiscount(Station fromStation, MetroCard metroCard, long travelCharges) {
        metroCard.checkIfReturnJourney(fromStation);
        return calculateDiscount(metroCard, travelCharges);
    }

    private MetroCard getMetroCard(String metroCardNumber) {
        Optional<MetroCard> metroCard = metroCardRepository.findByNumber(metroCardNumber) ;
        if(!metroCard.isPresent())
            throw new MetroCardNotFoundException(DOES_NOT_EXIST_WITH_GIVEN_NUMBER_ERROR);
        return metroCard.get();
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
        return stationSummaryRepository.getSummaryDtoByStation();
    }

}
