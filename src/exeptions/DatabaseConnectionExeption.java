package exeptions;

public class DatabaseConnectionExeption extends RuntimeException {
    public DatabaseConnectionExeption(String message) {
        super(message);
    }
}
