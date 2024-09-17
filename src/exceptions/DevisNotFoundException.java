package exceptions;

public class DevisNotFoundException extends RuntimeException {
    public DevisNotFoundException(String message) {
        super(message);
    }
}
