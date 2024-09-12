package hangman.model;

import backend.academy.hangman.controller.Session;
import backend.academy.hangman.model.GameModelImpl;
import backend.academy.hangman.model.data.GameLevel;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameModelImplTest {

    private GameModelImpl gameModel;

    @BeforeEach
    void setUp() {
        gameModel = new GameModelImpl("src/test/java/resources/words.json", 6);
    }

    @Test
    void testGetCategories() {
        List<String> categories = Arrays.asList("animals", "fruits", "countries", "colors");

        gameModel.getCategories();

        assertEquals(categories, gameModel.getCategories());
    }

    @Test
    void testStartWithCategory() {
        List<String> wordsInAnimalCategoryWithHighGameLevel =
            Arrays.asList("crocodile", "chimpanzee", "hippopotamus", "rhinoceros", "platypus");

        Session testSession = gameModel.start(GameLevel.HIGH, "animals");

        assertTrue(wordsInAnimalCategoryWithHighGameLevel.contains(testSession.word().toLowerCase()));
    }

    @Test
    void testRightInputProcess() {
        Session beforeProcess = gameModel.start(GameLevel.EASY, "animals");

        Session afterProcess = gameModel.process(beforeProcess.word().charAt(0));

        assertEquals(6, afterProcess.attempts());
        assertTrue(afterProcess.usedChars().contains(beforeProcess.word().charAt(0)));
    }

    @Test
    void testWrongInputProcess() {
        gameModel.start(GameLevel.EASY, "animals");

        Session afterProcess = gameModel.process('1');

        assertEquals(5, afterProcess.attempts());
        assertTrue(afterProcess.usedChars().contains('1'));
    }

    @Test
    void testProcessingStateWithoutStarting() {
        assertThrows(IllegalStateException.class, () -> gameModel.process('A'));
    }

    @Test
    void testGameIsWon() {
        Session state = gameModel.start(GameLevel.EASY, "animals");

        for (char c : state.word().toCharArray()) {
            state = gameModel.process(c);
        }

        assertEquals(6, state.attempts());
        assertTrue(gameModel.gameIsWon());
    }
}
