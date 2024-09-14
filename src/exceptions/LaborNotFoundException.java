package exceptions;

public class LaborNotFoundException extends RuntimeException {
    public LaborNotFoundException() {
        super("Labor not found");
    }

    public LaborNotFoundException(String message) {
        super(message);
    }
}
