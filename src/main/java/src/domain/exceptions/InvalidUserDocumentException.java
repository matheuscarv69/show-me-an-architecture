package src.domain.exceptions;

public class InvalidUserDocumentException extends RuntimeException {

    public InvalidUserDocumentException() {
        super("User cannot be registered, because your document is invalid");
    }
}
