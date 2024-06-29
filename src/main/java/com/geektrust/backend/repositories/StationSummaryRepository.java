package com.geektrust.backend.repositories;

import com.geektrust.backend.dtos.StationSummaryDto;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.entities.Station;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class StationSummaryRepository {
    HashMap<Station, StationSummaryDto> summaryDtoHashMap;

    public StationSummaryRepository() {
        this.summaryDtoHashMap = new HashMap<>();
        initializeHashMapForStations();
    }

    private void initializeHashMapForStations() {
        Arrays.stream(Station.values())
                .forEach(station -> summaryDtoHashMap.put(station, new StationSummaryDto(station)));
    }

    public void updateStationSummaryDto(Station fromStation, PassengerType passengerType, long totalAmount, long discount) {
        getSummaryDtoHashMap().put(fromStation, getSummaryDtoHashMap().get(fromStation)
                .updateStationDto(passengerType, totalAmount, discount));
    }

    public ArrayList<StationSummaryDto> getSummaryDtoByStation() {
        ArrayList<StationSummaryDto> stationSummaryList = new ArrayList<>(getSummaryDtoHashMap().values());
        stationSummaryList.sort((stationDto1, stationDto2) ->
                stationDto2.getStation().toString().compareTo(stationDto1.getStation().toString()));
        return stationSummaryList;
    }
    private HashMap<Station, StationSummaryDto> getSummaryDtoHashMap() {
        return summaryDtoHashMap;
    }
}
