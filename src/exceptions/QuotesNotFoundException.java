package exceptions;

public class QuotesNotFoundException extends RuntimeException {
    public QuotesNotFoundException() {
        super("Quote not found");
    }

    public QuotesNotFoundException(String message) {
        super(message);
    }
}
