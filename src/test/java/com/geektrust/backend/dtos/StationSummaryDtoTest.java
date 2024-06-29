package com.geektrust.backend.dtos;

import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.entities.Station;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Station Summary DTO Test")
class StationSummaryDtoTest {

    static StationSummaryDto airportStationDto;
    static StationSummaryDto centralStationDto;

    @BeforeAll
    static void setupDtoObject(){
        centralStationDto = new StationSummaryDto( Station.CENTRAL);
        airportStationDto = new StationSummaryDto( Station.AIRPORT);
        centralStationDto.updateStationDto(PassengerType.ADULT,200,100);
        centralStationDto.updateStationDto(PassengerType.SENIOR_CITIZEN,400,50);
        airportStationDto.updateStationDto(PassengerType.SENIOR_CITIZEN,1000,30);
        airportStationDto.updateStationDto(PassengerType.SENIOR_CITIZEN,1000,20);
        airportStationDto.updateStationDto(PassengerType.KID,500,50);
    }

    @Test
    @DisplayName("1. Test airport station DTO details")
    void toStringTestAirportStationDto() {
        String expectedOutput =  "TOTAL_COLLECTION AIRPORT 2500 100\n"+
        "PASSENGER_TYPE_SUMMARY\n"+
        "SENIOR_CITIZEN 2\n"+
        "KID 1";
        Assertions.assertEquals(expectedOutput,airportStationDto.toString());
    }

    @Test
    @DisplayName("2. Test central station DTO details")
    void toStringTestCentralStationDto() {
        String expectedOutput = "TOTAL_COLLECTION CENTRAL 600 150\n" +
                "PASSENGER_TYPE_SUMMARY\n" +
                "ADULT 1\n" +
                "SENIOR_CITIZEN 1";
        Assertions.assertEquals(expectedOutput, centralStationDto.toString());
    }
}