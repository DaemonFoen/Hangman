package backend.academy.hangman.model.data.api;

import backend.academy.hangman.model.data.GAME_LEVEL;
import backend.academy.hangman.model.data.Word;
import java.util.List;

public interface WordsRepository {

    Word getWord(GAME_LEVEL level, String category);

    Word getRandomCategoryWord(GAME_LEVEL level);

    List<String> getCategories();
}
