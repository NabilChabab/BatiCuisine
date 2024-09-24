package exceptions;

public class ProjectsNotFoundException extends RuntimeException {
    public ProjectsNotFoundException() {
        super("Project not found");
    }

    public ProjectsNotFoundException(String message) {
        super(message);
    }
}