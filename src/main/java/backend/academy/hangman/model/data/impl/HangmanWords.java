package backend.academy.hangman.model.data.impl;

import backend.academy.hangman.exception.SourceLoadException;
import backend.academy.hangman.model.data.WordsRepository;
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
    public Word getWord(GameLevel level, String category) {
        return getRandomWordFromCategory(level, categories.get(category));
    }

    @Override
    public Word getRandomCategoryWord(GameLevel level) {
        if (categories.isEmpty()) {
            log.error("Список слов не содержит ни одной категории, возможно файл с ресурсами пуст");
            throw new SourceLoadException("Список слов не содержит ни одной категории");
        }

        var random = getRandomNumber() % categories.keySet().size();
        var iterator = categories.keySet().iterator();
        while (random > 0) {
            iterator.next();
            random--;
        }

        return getRandomWordFromCategory(level, categories.get(iterator.next()));
    }

    private Word getRandomWordFromCategory(GameLevel level, Category category) {
        return switch (level) {
            case EASY -> category.easy().get(getRandomNumber() % category.easy().size());
            case MEDIUM -> category.medium().get(getRandomNumber() % category.medium().size());
            case HIGH -> category.hard().get(getRandomNumber() % category.hard().size());
        };
    }
}
