package backend.academy.hangman.exception;

/**
 * Кидается, когда возникают проблемы с загрузкой ресурсов, такие как {@link java.io.FileNotFoundException}.
 */
public class SourceLoadException extends RuntimeException {

    public SourceLoadException(String message) {
        super(message);
    }

}
