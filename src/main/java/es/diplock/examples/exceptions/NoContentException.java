package es.diplock.examples.exceptions;

public class NoContentException extends RuntimeException {
    public NoContentException(String message) {
        super(message);
    }
}
