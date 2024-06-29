package com.geektrust.backend;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

@DisplayName("App Test")
class AppTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("Integration Test #1")
    void runTest1(){

        //Arrange
        List<String> arguments =new ArrayList<>() ;
        arguments.add("sample_input/input1.txt");

        String expectedOutput = "TOTAL_COLLECTION CENTRAL 300 0\n" +
                "PASSENGER_TYPE_SUMMARY\n" +
                "ADULT 1\n" +
                "SENIOR_CITIZEN 1\n" +
                "TOTAL_COLLECTION AIRPORT 403 100\n" +
                "PASSENGER_TYPE_SUMMARY\n" +
                "ADULT 2\n" +
                "KID 2";
        //Act
        App.run(arguments);

        //Assert
        Assertions.assertEquals(expectedOutput.trim().replaceAll("\\s", ""),
                outputStreamCaptor.toString().trim().replaceAll("\\s", ""));

    }

    @Test
    @DisplayName("Integration Test #2")
    void runTest2(){

        //Arrange
        List<String> arguments =new ArrayList<>() ;
        arguments.add("sample_input/input2.txt");

        String expectedOutput = "TOTAL_COLLECTION CENTRAL 457 50\n" +
                "PASSENGER_TYPE_SUMMARY\n" +
                "ADULT 2\n" +
                "SENIOR_CITIZEN 1\n" +
                "TOTAL_COLLECTION AIRPORT 252 100\n" +
                "PASSENGER_TYPE_SUMMARY\n" +
                "ADULT 1\n" +
                "KID 1\n" +
                "SENIOR_CITIZEN 1";
        //Act
        App.run(arguments);

        //Assert
        Assertions.assertEquals(expectedOutput.trim().replaceAll("\\s", ""),
                outputStreamCaptor.toString().trim().replaceAll("\\s", ""));

    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

}
