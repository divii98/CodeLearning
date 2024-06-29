package com.geektrust.backend;

import com.geektrust.backend.appConfig.ApplicationConfig;
import com.geektrust.backend.commands.CommandInvoker;
import com.geektrust.backend.exceptions.InvalidCommandException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    private static final int INPUT_FILE_INDEX = 0;
    private static final int COMMAND_NAME_INDEX = 0;
    private static final String TEXT_FILE_EXTENSION = ".txt";
    // ./gradlew run --args="sample_input/input1.txt"
    public static void main(String[] args) {
        if (args[INPUT_FILE_INDEX].contains(TEXT_FILE_EXTENSION)) {
            List<String> arguments = new ArrayList<>(Arrays.asList(args));
            run(arguments);
        }
    }

    public static void run(List<String> arguments) {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        CommandInvoker commandInvoker = applicationConfig.getCommandInvoker();
        String inputFile = arguments.get(INPUT_FILE_INDEX);
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line = reader.readLine();
            while (line != null) {
                List<String> tokens = Arrays.asList(line.split(" "));
                commandInvoker.executeCommand(tokens.get(COMMAND_NAME_INDEX), tokens);
                // read next line
                line = reader.readLine();
            }
        } catch (IOException | InvalidCommandException e) {
            e.printStackTrace();
        }
    }
}
