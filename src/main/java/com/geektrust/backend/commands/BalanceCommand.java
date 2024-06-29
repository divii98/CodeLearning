package com.geektrust.backend.commands;

import com.geektrust.backend.exceptions.MetroCardAlreadyExistException;
import com.geektrust.backend.services.IStationService;

import java.util.List;

public class BalanceCommand implements ICommand {
    private final IStationService stationService;
    private static final int MetroCardNumberIndexInInput = 1;
    private static final int MetroCardBalanceIndexInInput = 2;

    public BalanceCommand(IStationService stationService) {
        this.stationService = stationService;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            stationService.metroCardBalance(tokens.get(MetroCardNumberIndexInInput)
                    ,Long.parseLong(tokens.get(MetroCardBalanceIndexInInput)));
        }catch (NumberFormatException | ArithmeticException  |MetroCardAlreadyExistException e){
            System.out.println(e.getMessage());
        }
    }
}
