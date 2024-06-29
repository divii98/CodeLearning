package com.geektrust.backend.entities;

import com.geektrust.backend.exceptions.InvalidCommandException;

public class TravelCharges {
    private static final long ADULT_CHARGES = 200;
    public static final long SENIOR_CITIZEN_CHARGES = 100;
    public static final long KID_CHARGES = 50;
    //Discount of 50%
    public static final double DISCOUNT_PERCENTAGE = 0.5;
    public static final String WRONG_PASSENGER_TYPE_PASSED_ERROR = "Wrong PassengerType Passed";

    public static long getCharges(PassengerType passengerType) {
        switch (passengerType){
            case ADULT: return ADULT_CHARGES;
            case SENIOR_CITIZEN: return SENIOR_CITIZEN_CHARGES;
            case KID: return KID_CHARGES;
            default: throw new InvalidCommandException(WRONG_PASSENGER_TYPE_PASSED_ERROR);
        }
    }
//    To calculate discount of 50%
    public static  long getDiscountedPrice(long travelCharges){
        return (long)(travelCharges*DISCOUNT_PERCENTAGE);
    }

}
