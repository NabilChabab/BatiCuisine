package exceptions;

public class ComponentNotFoundException extends RuntimeException {
    public ComponentNotFoundException() {
        super("Component not found");
    }

    public ComponentNotFoundException(String message) {
        super(message);
    }
}