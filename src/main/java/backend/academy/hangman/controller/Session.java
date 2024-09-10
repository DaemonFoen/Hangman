package backend.academy.hangman.controller;

import java.util.Set;

public record Session(String word, String hint, int attempts, Set<Character> usedChars) {

}
