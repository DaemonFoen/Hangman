package backend.academy.hangman.exception;

/**
 * Кидается, когда возникают проблемы с загрузкой ресурсов, такие как {@link java.io.FileNotFoundException}.
 */
public class SourceLoadException extends RuntimeException {

    public SourceLoadException() {
        super();
    }

    public SourceLoadException(String message) {
        super(message);
    }

    public SourceLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public SourceLoadException(Throwable cause) {
        super(cause);
    }

}
