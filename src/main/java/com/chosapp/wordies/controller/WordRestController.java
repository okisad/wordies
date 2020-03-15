package com.chosapp.wordies.controller;


import com.chosapp.wordies.dto.external_word.ExternalWord;
import com.chosapp.wordies.service.word.WordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequestMapping("/words")
public class WordRestController {

    private final WordService wordService;

    public WordRestController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping("/{word}")
    public Mono<ExternalWord> findWord(@PathVariable("word") String word) throws IOException {
        ExternalWord externalWord = wordService.findWord(word);
        return Mono.just(externalWord);
    }

}
