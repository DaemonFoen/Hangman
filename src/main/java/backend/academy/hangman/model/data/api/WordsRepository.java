package backend.academy.hangman.model.data.api;

import backend.academy.hangman.model.data.GameLevel;
import backend.academy.hangman.model.data.Word;
import java.util.List;

public interface WordsRepository {

    Word getWord(GameLevel level, String category);

    Word getRandomCategoryWord(GameLevel level);

    List<String> getCategories();
}
