package backend.academy.hangman.model.data.impl;

import java.util.List;

record Category(List<Word> easy, List<Word> medium, List<Word> hard) {

}
