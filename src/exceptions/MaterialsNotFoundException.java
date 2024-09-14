package exceptions;

public class MaterialsNotFoundException extends RuntimeException {
    public MaterialsNotFoundException() {
        super("Material not found");
    }

    public MaterialsNotFoundException(String message) {
        super(message);
    }
}