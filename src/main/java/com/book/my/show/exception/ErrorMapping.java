package com.book.my.show.exception;

import lombok.Getter;

@Getter
public enum ErrorMapping {
    BMS001("BMS001", "No theatre is present for given city"),
    BMS002("BMS002", "No theatre is present for given city and movie combination"),
    BMS003("BMS003", "No Auditorium and show found for given city and theatre combination"),
    BMS004("BMS004", "No Seat available for given theatre, movie, show and movieDate combination"),
    BMS005("BMS005", "UserId is either null/empty"),
    BMS006("BMS006", "User has requested ticket history with invalid limit"),
    BMS007("BMS007", "No ticket history present against provided userId"),
    BMS008("BMS008", "No ticket present for provided userId and ticketId combination"),
    BMS009("BMS009", "ticketId is either null/empty"),
    BMS010("BMS010", "Provided offset is invalid. Offset never be less than zero"),
    BMS011("BMS011", "City Name is either null/empty"),
    BMS012("BMS012", "Movie Name is either null/empty"),
    BMS013("BMS013", "Theatre Name is either null/empty"),
    BMS014("BMS014", "Show Time is either null/empty"),
    BMS015("BMS015", "Show Day is either null/empty"),
    BMS016("BMS016", "Failed to process the payment due to some issues at payment service side/ due to insufficient balance"),
    BMS017("BMS017", "Already filled some of the seats that user has requested"),
    BMS018("BMS018", "No seat left as per the user's seat combination request");

    private final String code;
    private final String message;

    ErrorMapping(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
