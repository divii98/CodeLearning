package com.geektrust.backend.repositories;

import com.geektrust.backend.dtos.StationSummaryDto;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.entities.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class StationSummaryRepositoryTest {

    private StationSummaryRepository stationSummaryRepository;

    @BeforeEach
    public void setUp() {
        stationSummaryRepository = new StationSummaryRepository();
    }

    @Test
    public void testInitializeHashMapForStations() {
        ArrayList<StationSummaryDto> summaryList = stationSummaryRepository.getSummaryDtoByStation();
        assertEquals(Station.values().length, summaryList.size());

        HashMap<Station, Boolean> stationExistsMap = new HashMap<>();
        for (Station station : Station.values()) {
            stationExistsMap.put(station, false);
        }

        for (StationSummaryDto dto : summaryList) {
            assertNotNull(dto);
            stationExistsMap.put(dto.getStation(), true);
        }

        for (Station station : Station.values()) {
            assertTrue(stationExistsMap.get(station));
        }
    }

    @Test
    public void testUpdateStationSummaryDto() {
        Station fromStation = Station.AIRPORT;
        PassengerType passengerType = PassengerType.ADULT;
        long totalAmount = 100;
        long discount = 10;

        stationSummaryRepository.updateStationSummaryDto(fromStation, passengerType, totalAmount, discount);

        ArrayList<StationSummaryDto> summaryList = stationSummaryRepository.getSummaryDtoByStation();
        StationSummaryDto updatedDto = summaryList.stream()
                .filter(dto -> dto.getStation().equals(fromStation))
                .findFirst()
                .orElse(null);

        assertNotNull(updatedDto);
        assertEquals(fromStation, updatedDto.getStation());
    }

    @Test
    public void testGetSummaryDtoByStation() {
        ArrayList<StationSummaryDto> summaryList = stationSummaryRepository.getSummaryDtoByStation();
        assertEquals(Station.values().length, summaryList.size());

        // Verify that the list is sorted in descending order
        for (int i = 0; i < summaryList.size() - 1; i++) {
            String currentStation = summaryList.get(i).getStation().toString();
            String nextStation = summaryList.get(i + 1).getStation().toString();
            assertTrue(currentStation.compareTo(nextStation) > 0);
        }
    }
}
