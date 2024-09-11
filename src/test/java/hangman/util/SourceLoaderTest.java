package hangman.util;

import backend.academy.hangman.model.data.GAME_LEVEL;
import backend.academy.hangman.model.data.HangmanWords;
import backend.academy.hangman.util.SourceLoader;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SourceLoaderTest {

    @Test
    void testLoadSuccess() {
        HangmanWords result = SourceLoader.load("src/test/java/resources/words.json");
        assertEquals("animals", result.getCategories().getFirst());
        assertEquals("cat", result.getWord(GAME_LEVEL.valueOf("EASY"), "animals").word());
    }

}
