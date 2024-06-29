package com.geektrust.backend.entities;

public class MetroCard {
    public static final String SHOULD_BE_POSITIVE_ERROR = "Balance should be positive";
    private final String cardNumber;
    private long balance;
    private Station fromStation;
    private Station toStation;
    private boolean isReturnJourney;
    private static final long MINIMUM_BALANCE = 0;
    private final static double SERVICE_CHARGE_PERCENT = 0.02;

    public MetroCard(String uniqueId, long balance) {
        if (balance < MINIMUM_BALANCE)
            throw new ArithmeticException(SHOULD_BE_POSITIVE_ERROR);
        this.cardNumber = uniqueId;
        this.balance = balance;
        fromStation = null;
        toStation =null;
        isReturnJourney = false;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public long getBalance() {
        return balance;
    }

    public void deductMoney(long amount) {
        balance -= amount;
    }

    public long rechargeCard(long amount) {
        amount = amount-balance;
        long serviceCharge = (long) (amount * SERVICE_CHARGE_PERCENT);
        this.balance += amount;
        return serviceCharge;
    }

    public void setFromStation(Station fromStation) {
        this.fromStation = fromStation;
    }



    public void setToStation(Station toStation) {
        this.toStation = toStation;
    }

    public void checkIfReturnJourney(Station fromStation) {
        if (this.toStation != null && this.toStation.equals(fromStation)) {
            this.isReturnJourney = true;
        }
    }

    public boolean isReturnJourney() {
        return isReturnJourney;
    }

    public void updateJourney(long travelCharges, Station fromStation) {
        deductMoney(travelCharges);
        if (this.isReturnJourney) {
            setToStation(null);
            setFromStation(null);
            this.isReturnJourney = false;
        } else {
            this.fromStation = fromStation;
            this.toStation = fromStation.equals(Station.AIRPORT) ? Station.CENTRAL : Station.AIRPORT;
        }
    }



}


