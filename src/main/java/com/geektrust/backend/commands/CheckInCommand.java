package com.geektrust.backend.commands;

import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.entities.Station;
import com.geektrust.backend.exceptions.MetroCardNotFoundException;
import com.geektrust.backend.services.IStationService;

import java.util.List;

public class CheckInCommand implements ICommand {
    private  final IStationService metroCardService;
    private static final int MetroCardNumberIndexInInput = 1;
    private static final int PassengerTypeIndexInInput = 2;
    private static final int StationIndexInInput = 3;


    public CheckInCommand(IStationService metroCardService) {
        this.metroCardService = metroCardService;
    }

    @Override
    public void execute(List<String> tokens) {
        try{
            metroCardService.checkInStation(tokens.get(MetroCardNumberIndexInInput),
                    PassengerType.valueOf(tokens.get(PassengerTypeIndexInInput)),
                    Station.valueOf(tokens.get(StationIndexInInput)));
        }catch(MetroCardNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}
