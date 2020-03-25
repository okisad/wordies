package com.chosapp.wordies.service.word_crawler;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
class WordCrawlerServiceTest {

    @Autowired
    WordCrawlerService wordCrawlerService;

    @Test
    void getWord() throws IOException {
        wordCrawlerService.getWord("measure");
    }
}