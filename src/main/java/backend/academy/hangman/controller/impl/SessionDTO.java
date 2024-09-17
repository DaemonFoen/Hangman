package backend.academy.hangman.controller.impl;

import java.util.Set;

public record SessionDTO(String word, String hint, int attempts, Set<Character> usedChars) {

}
