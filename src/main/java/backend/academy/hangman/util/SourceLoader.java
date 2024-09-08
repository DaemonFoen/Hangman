package backend.academy.hangman.util;

import backend.academy.hangman.model.data.HangmanWords;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@Log4j2
@UtilityClass
public class SourceLoader {

    private static final String SOURCE_PATH = "src/main/resources/words.json";

    public static HangmanWords load() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(SOURCE_PATH), HangmanWords.class);
        } catch (IOException e) {
            log.error(e);
            log.error("Failed to load words.json");
            System.exit(1);
            return null;
        }
    }
}