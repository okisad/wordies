package com.chosapp.wordies.service.word;

import com.chosapp.wordies.dao.external_word.ExternalWordDAO;
import com.chosapp.wordies.dto.external_word.ExternalWord;
import com.chosapp.wordies.service.word_crawler.WordCrawlerService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class WordService {

    private WordCrawlerService wordCrawlerService;
    private ExternalWordDAO externalWordDAO;

    public WordService(WordCrawlerService wordCrawlerService, ExternalWordDAO externalWordDAO) {
        this.wordCrawlerService = wordCrawlerService;
        this.externalWordDAO = externalWordDAO;
    }

    public ExternalWord findWord(String wordName) throws IOException {
        Optional<ExternalWord> externalWord = externalWordDAO.findByWordName(wordName);
        if (externalWord.isPresent()){
            return externalWord.get();
        }else {
            return wordCrawlerService.getWord(wordName);
        }
    }

}
