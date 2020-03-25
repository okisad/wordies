package com.chosapp.wordies.controller;


import com.chosapp.wordies.dto.external_word.BaseWord;
import com.chosapp.wordies.dto.user_word.UserWord;
import com.chosapp.wordies.dto.user_word.UserWordCount;
import com.chosapp.wordies.service.user_word.UserWordService;
import com.chosapp.wordies.service.word.WordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/words")
public class WordRestController {

    private final WordService wordService;
    private final UserWordService userWordService;

    public WordRestController(WordService wordService, UserWordService userWordService) {
        this.wordService = wordService;
        this.userWordService = userWordService;
    }

    @GetMapping("/{word}")
    public ResponseEntity<List<BaseWord>> findWord(@PathVariable("word") String word) throws InterruptedException {
        System.out.println(word);
        List<BaseWord> baseWords = wordService.findWord(word);
        return ResponseEntity.ok(baseWords);
    }

    @GetMapping("/random")
    public ResponseEntity<UserWord> findWord() throws InterruptedException {
        Optional<UserWord> baseWords = userWordService.findNotShowedWord();
        return baseWords.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/user-words")
    public ResponseEntity<UserWord> saveUserWord(@RequestBody UserWord userWord) throws InterruptedException {
        UserWord baseWords = userWordService.save(userWord);
        return ResponseEntity.ok(baseWords);
    }

    @GetMapping("/count")
    public ResponseEntity<UserWordCount> findUserWordCount() throws InterruptedException {
        UserWordCount userWordCount = userWordService.findUserWordCount();
        return ResponseEntity.ok(userWordCount);
    }

}
