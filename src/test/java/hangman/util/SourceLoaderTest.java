package hangman.util;

import backend.academy.hangman.model.data.GameLevel;
import backend.academy.hangman.model.data.HangmanWords;
import backend.academy.hangman.util.SourceLoader;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SourceLoaderTest {

    @Test
    void testLoadSuccess() {
        HangmanWords result = SourceLoader.load("src/test/java/resources/words.json");
        List<String> wordsInAnimalCategoryWithHighGameLevel =
            Arrays.asList("crocodile", "chimpanzee", "hippopotamus", "rhinoceros", "platypus");

        assertEquals("animals", result.getCategories().getFirst());

        assertTrue(wordsInAnimalCategoryWithHighGameLevel.contains(
            result.getWord(GameLevel.valueOf("HIGH"), "animals").word()));
    }

}
