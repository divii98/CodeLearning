package com.geektrust.backend.services;

import com.geektrust.backend.dtos.StationSummaryDto;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.entities.Station;
import com.geektrust.backend.exceptions.MetroCardAlreadyExistException;
import com.geektrust.backend.exceptions.MetroCardNotFoundException;
import com.geektrust.backend.repositories.MetroCardRepository;
import com.geektrust.backend.repositories.StationSummaryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class StationServiceTest {

    private MetroCardRepository metroCardRepository;
    private StationSummaryRepository stationSummaryRepository;
    private StationService stationService;

    @BeforeEach
    public void setUp() {
        metroCardRepository = mock(MetroCardRepository.class);
        stationSummaryRepository = mock(StationSummaryRepository.class);
        stationService = new StationService(metroCardRepository, stationSummaryRepository);
    }

    @Test
    public void testMetroCardBalance_MetroCardAlreadyExist() {
        when(metroCardRepository.isExistByNumber(anyString())).thenReturn(true);

        assertThrows(MetroCardAlreadyExistException.class, () -> {
            stationService.metroCardBalance("12345", 100);
        });

        verify(metroCardRepository, never()).save(any(MetroCard.class));
    }

    @Test
    public void testMetroCardBalance_MetroCardDoesNotExist() throws MetroCardAlreadyExistException {
        when(metroCardRepository.isExistByNumber(anyString())).thenReturn(false);

        stationService.metroCardBalance("12345", 100);

        ArgumentCaptor<MetroCard> metroCardCaptor = ArgumentCaptor.forClass(MetroCard.class);
        verify(metroCardRepository).save(metroCardCaptor.capture());

        MetroCard savedMetroCard = metroCardCaptor.getValue();
        assertEquals("12345", savedMetroCard.getCardNumber());
        assertEquals(100, savedMetroCard.getBalance());
    }


    @Test
    public void testCheckInStation_MetroCardNotFound() {
        when(metroCardRepository.findByNumber(anyString())).thenReturn(Optional.empty());

        assertThrows(MetroCardNotFoundException.class, () -> {
            stationService.checkInStation("12345", PassengerType.ADULT, Station.AIRPORT);
        });

        verify(metroCardRepository, never()).save(any(MetroCard.class));
    }


    @Test
    public void testGetStationSummary() {
        StationSummaryDto dto1 = mock(StationSummaryDto.class);
        StationSummaryDto dto2 = mock(StationSummaryDto.class);
        ArrayList<StationSummaryDto> summaryList = new ArrayList<>();
        summaryList.add(dto1);
        summaryList.add(dto2);

        when(stationSummaryRepository.getSummaryDtoByStation()).thenReturn((ArrayList<StationSummaryDto>) summaryList);
        List<StationSummaryDto> result = stationService.getStationSummary();
        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
    }
}
