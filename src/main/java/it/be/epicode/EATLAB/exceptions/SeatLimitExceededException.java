package it.be.epicode.EATLAB.exceptions;

public class SeatLimitExceededException extends RuntimeException{

    public SeatLimitExceededException(String message) {
        super(message);
    }

}
