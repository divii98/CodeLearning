package com.geektrust.backend.services;

import com.geektrust.backend.dtos.StationSummaryDto;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.entities.Station;
import com.geektrust.backend.exceptions.MetroCardAlreadyExistException;
import com.geektrust.backend.exceptions.MetroCardNotFoundException;

import java.util.List;

public interface IStationService {
    public void metroCardBalance(String metroCardNumber, long balance) throws ArithmeticException, MetroCardAlreadyExistException;
    public void checkInStation(String metroCardNumber, PassengerType passengerType, Station fromStation)
            throws MetroCardNotFoundException;
    public List<StationSummaryDto> getStationSummary();
}
