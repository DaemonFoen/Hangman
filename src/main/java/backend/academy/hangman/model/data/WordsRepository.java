package backend.academy.hangman.model.data;

import backend.academy.hangman.model.data.impl.GameLevel;
import backend.academy.hangman.model.data.impl.Word;
import java.util.List;

public interface WordsRepository {

    Word getWord(GameLevel level, String category);

    Word getRandomCategoryWord(GameLevel level);

    List<String> getCategories();
}
