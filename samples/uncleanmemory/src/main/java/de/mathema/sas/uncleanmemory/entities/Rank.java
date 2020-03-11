package de.mathema.sas.uncleanmemory.entities;

public enum Rank {
    TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"), TEN("10"),
    JACK("J"), QUEEN("Q"), KING("K"), ACE("A");

    private final String faceValue;

    Rank(String faceValue) {
        this.faceValue = faceValue;
    }

    @Override
    public String toString() {
        return faceValue;
    }
}
