package de.mathema.sas.uncleanmemory.entities;

public enum Suit {
    HEART("\u2661"), DIAMOND("\u2662"), CLUB("\u2663"), SPADE("\u2660");

    private final String suitValue;

    Suit(String suitValue) {
        this.suitValue = suitValue;
    }

    @Override
    public String toString() {
        return suitValue;
    }
}
