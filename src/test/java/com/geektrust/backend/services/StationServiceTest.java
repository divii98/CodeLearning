package com.geektrust.backend.services;

import com.geektrust.backend.dtos.StationSummaryDto;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.entities.Station;
import com.geektrust.backend.exceptions.MetroCardNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class StationServiceTest {
    private static final String TEST_METRO_CARD_NUMBER = "12345";
    private static final int EXPECTED_SUMMARY_LIST_SIZE = 2;
    @InjectMocks
    private StationService stationService;
    @Mock
    private HashMap<Station, StationSummaryDto> stationSummaryHashMap;
    @Mock
    private HashMap<String, MetroCard> metroCards;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(stationSummaryHashMap.get(Station.CENTRAL)).thenReturn(new StationSummaryDto(Station.CENTRAL));
        when(stationSummaryHashMap.get(Station.AIRPORT)).thenReturn(new StationSummaryDto(Station.AIRPORT));
    }


    @Test
    public void testCheckInStation_CardNotFound() {
        String metroCardNumber = TEST_METRO_CARD_NUMBER;
        PassengerType passengerType = PassengerType.ADULT;
        Station fromStation = Station.CENTRAL;

        when(metroCards.get(metroCardNumber)).thenReturn(null);

        assertThrows(MetroCardNotFoundException.class, () ->
                stationService.checkInStation(metroCardNumber, passengerType, fromStation));
    }

    @Test
    public void testGetStationSummary() {
        StationSummaryDto centralSummary = new StationSummaryDto(Station.CENTRAL);
        StationSummaryDto airportSummary = new StationSummaryDto(Station.AIRPORT);
        List<StationSummaryDto> summaryList = new ArrayList<>();
        summaryList.add(centralSummary);
        summaryList.add(airportSummary);
        when(stationSummaryHashMap.values()).thenReturn(summaryList);

        List<StationSummaryDto> summaries = stationService.getStationSummary();

        assertEquals(EXPECTED_SUMMARY_LIST_SIZE, summaries.size());
        assertEquals(Station.CENTRAL, summaries.get(0).getStation());
        assertEquals(Station.AIRPORT, summaries.get(1).getStation());
    }
}
