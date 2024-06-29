package com.geektrust.backend.commands;

import com.geektrust.backend.exceptions.InvalidCommandException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandInvoker {
    private static final Map<String,ICommand> commandMap = new HashMap<>();
    private static final String INVALID_COMMAND_MESSAGE = "Invalid command passed";

    //Register all the commands
    public void register(String commandName, ICommand command){
        commandMap.put(commandName,command);
    }

    //Get the registered command
    private ICommand getCommand(String commandName){
        return commandMap.get(commandName);
    }

    //Execute the given command
    public void executeCommand(String commandName, List<String> tokens){
        ICommand command = getCommand(commandName);
        if(command == null)
            throw new InvalidCommandException(INVALID_COMMAND_MESSAGE);
        command.execute(tokens);
    }
}
