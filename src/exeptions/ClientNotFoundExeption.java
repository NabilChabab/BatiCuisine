package exeptions;

public class ClientNotFoundExeption extends RuntimeException {
    public ClientNotFoundExeption(String message) {
        super(message);
    }
}
