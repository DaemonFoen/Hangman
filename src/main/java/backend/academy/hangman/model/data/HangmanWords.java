package backend.academy.hangman.model.data;

import backend.academy.hangman.exception.SourceLoadException;
import backend.academy.hangman.model.data.api.WordsRepository;
import com.beust.jcommander.internal.Lists;
import java.util.List;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import static backend.academy.hangman.util.RandomGenerator.getRandomNumber;

@Log4j2
public record HangmanWords(Map<String, Category> categories) implements WordsRepository {

    @Override
    public List<String> getCategories() {
        return Lists.newArrayList(categories.keySet());
    }

    @Override
    public Word getWord(GAME_LEVEL level, String category) {
        return categories.get(category).getRandomWordFromCategory(level);
    }

    @Override
    public Word getRandomCategoryWord(GAME_LEVEL level) {
        if (categories.isEmpty()) {
            log.error("Список слов не содержит ни одной категории");
            throw new SourceLoadException("Список слов не содержит ни одной категории");
        }

        var random = getRandomNumber() % categories.keySet().size();
        var iterator = categories.keySet().iterator();
        while (random > 0) {
            iterator.next();
            random--;
        }

        return categories.get(iterator.next()).getRandomWordFromCategory(level);
    }
}
