package com.geektrust.backend.appConfig;

import com.geektrust.backend.commands.*;
import com.geektrust.backend.services.IStationService;
import com.geektrust.backend.services.StationService;

public class ApplicationConfig {
    private static final String BALANCE_COMMAND_NAME = "BALANCE";
    private static final String CHECK_IN_COMMAND_NAME = "CHECK_IN";
    private static final String PRINT_SUMMARY_COMMAND_NAME = "PRINT_SUMMARY";

    private final IStationService metroCardService = new StationService();
    private final BalanceCommand balanceCommand = new BalanceCommand(metroCardService);
    private final CheckInCommand checkInCommand = new CheckInCommand(metroCardService);
    private final PrintSummaryCommand printSummaryCommand = new PrintSummaryCommand(metroCardService);
    private final CommandInvoker commandInvoker = new CommandInvoker();

    public CommandInvoker getCommandInvoker(){
        commandInvoker.register(BALANCE_COMMAND_NAME,balanceCommand);
        commandInvoker.register(CHECK_IN_COMMAND_NAME,checkInCommand);
        commandInvoker.register(PRINT_SUMMARY_COMMAND_NAME,printSummaryCommand);
        return commandInvoker;
    }
}
