package com.geektrust.backend.dtos;

import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.entities.Station;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StationSummaryDto {
    private final Station station;
    private long totalAmount;
    private long totalDiscount;
    private final Map<PassengerType, Long> passengerCount;
    private static final long INITIAL_VALUE = 0;
    private static final long INCREASE_COUNT_BY = 1;



    public StationSummaryDto(Station station) {
        this.station = station;
        totalAmount = INITIAL_VALUE;
        totalDiscount = INITIAL_VALUE;
        passengerCount = new HashMap<>();

    }

    public StationSummaryDto updateStationDto(PassengerType passengerType,long amount,long discount) {
        totalAmount += amount;
        totalDiscount += discount;
        passengerCount.put(passengerType,
                passengerCount.getOrDefault(passengerType,(long) INITIAL_VALUE)+INCREASE_COUNT_BY);
        return this;
    }

    @Override
    public String toString() {
        return "TOTAL_COLLECTION "+station+" "+totalAmount+" "+totalDiscount+
                printPassengersCount();
    }

    public Station getStation() {
        return station;
    }

    private String printPassengersCount() {
        StringBuilder passengerCountDetails = new StringBuilder("\nPASSENGER_TYPE_SUMMARY");
        ArrayList<Map.Entry<PassengerType,Long>> passengerList = getSortedList();
        for(Map.Entry<PassengerType,Long> passengers : passengerList){
            passengerCountDetails.append("\n")
                    .append(passengers.getKey().toString())
                    .append(" ")
                    .append(passengers.getValue());
        }
        return passengerCountDetails.toString();
    }

    private ArrayList<Map.Entry<PassengerType, Long>> getSortedList() {
        ArrayList<Map.Entry<PassengerType,Long>> passengerList = new ArrayList<>(passengerCount.entrySet());
        passengerList.sort((entry1,entry2) -> {
            int valueComparison = entry2.getValue().compareTo(entry1.getValue());
            if(valueComparison != 0)
                return valueComparison;
            else
                return entry1.getKey().toString().compareTo(entry2.getKey().toString());
        });
        return passengerList;
    }

}
