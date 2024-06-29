package com.geektrust.backend.commands;

import com.geektrust.backend.dtos.StationSummaryDto;
import com.geektrust.backend.services.IStationService;

import java.util.List;

public class PrintSummaryCommand implements ICommand {
    private final IStationService metroCardService;

    public PrintSummaryCommand(IStationService metroCardService) {
        this.metroCardService = metroCardService;
    }

    @Override
    public void execute(List<String> tokens) {
        List<StationSummaryDto> summaryDtoList = metroCardService.getStationSummary();
        for( StationSummaryDto stationSummary : summaryDtoList)
            System.out.println(stationSummary+"\n");
    }
}
