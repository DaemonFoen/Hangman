package hangman.model;

import backend.academy.hangman.controller.impl.SessionDTO;
import backend.academy.hangman.exception.InvalidInputException;
import backend.academy.hangman.model.impl.GameModelImpl;
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
    void testStartWithCategory() throws InvalidInputException {
        List<String> wordsInAnimalCategoryWithHighGameLevel =
            Arrays.asList("crocodile", "chimpanzee", "hippopotamus", "rhinoceros", "platypus");

        //Set animal category
        gameModel.updateGameState(3);
        gameModel.updateGameState(1);

        //set game leve: hard
        gameModel.updateGameState(2);
        gameModel.updateGameState(3);

        SessionDTO testSessionDTO = gameModel.start();

        assertTrue(wordsInAnimalCategoryWithHighGameLevel.contains(testSessionDTO.word().toLowerCase()));
    }

    @Test
    void testRightInputProcess() throws InvalidInputException {
        //Set animal category
        gameModel.updateGameState(3);
        gameModel.updateGameState(1);
        SessionDTO beforeProcess = gameModel.start();

        SessionDTO afterProcess = gameModel.process(beforeProcess.word().charAt(0));

        assertEquals(6, afterProcess.attempts());
        assertTrue(afterProcess.usedChars().contains(beforeProcess.word().charAt(0)));
    }

    @Test
    void testWrongInputProcess() throws InvalidInputException {
        gameModel.updateGameState(3);
        gameModel.updateGameState(1);
        gameModel.start();

        SessionDTO afterProcess = gameModel.process('1');

        assertEquals(5, afterProcess.attempts());
        assertTrue(afterProcess.usedChars().contains('1'));
    }

    @Test
    void testProcessingStateWithoutStarting() {
        assertThrows(IllegalStateException.class, () -> gameModel.process('A'));
    }

    @Test
    void testGameIsWon() throws InvalidInputException {
        gameModel.updateGameState(3);
        gameModel.updateGameState(1);
        SessionDTO state = gameModel.start();

        for (char c : state.word().toCharArray()) {
            state = gameModel.process(c);
        }

        assertEquals(6, state.attempts());
        assertTrue(gameModel.gameIsWon());
    }
}
