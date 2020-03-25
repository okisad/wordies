package com.chosapp.wordies.service.word;

import com.chosapp.wordies.dao.base_word.BaseWordDAO;
import com.chosapp.wordies.dao.user_word.UserWordDAO;
import com.chosapp.wordies.dto.external_word.BaseWord;
import com.chosapp.wordies.dto.user_word.UserWord;
import com.chosapp.wordies.service.word_crawler.WordCrawlerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class WordService {

    private WordCrawlerService wordCrawlerService;
    private BaseWordDAO baseWordDAO;
    private UserWordDAO userWordDAO;

    @Autowired
    public WordService(WordCrawlerService wordCrawlerService, BaseWordDAO baseWordDAO, UserWordDAO userWordDAO) {
        this.wordCrawlerService = wordCrawlerService;
        this.baseWordDAO = baseWordDAO;
        this.userWordDAO = userWordDAO;
    }

    public List<BaseWord> findWord(String wordName) {
        List<BaseWord> baseWords = baseWordDAO.findBaseWordsByName(wordName);
        if (!CollectionUtils.isEmpty(baseWords))
            return baseWords;
        List<BaseWord> fetchedBaseWords = fetchBaseWord(wordName);
        fetchedBaseWords = baseWordDAO.save(fetchedBaseWords);
        saveUserWords(fetchedBaseWords);
        return fetchedBaseWords;
    }

    private List<BaseWord> fetchBaseWord(String wordName) {
        int tryCount = 0;
        List<BaseWord> extractedBaseWords = Collections.emptyList();
        while (true) {
            try {
                extractedBaseWords = wordCrawlerService.getWord(wordName);
                break;
            } catch (IOException e) {
                if (tryCount++ == 3)
                    break;
                else
                    log.warn(String.format("Fetching of word process retry count : %d", tryCount));
            }
        }
        return extractedBaseWords;
    }

    private void saveUserWords(List<BaseWord> baseWords){
        if (CollectionUtils.isEmpty(baseWords)) return;
        List<UserWord> userWords = baseWords
                .stream()
                .map(baseWord -> {
                    UserWord userWord = new UserWord();
                    userWord.setUsername("oktay");
                    userWord.setWordMeaning(baseWord);
                    userWord.setBatchId(baseWord.getBatchId());
                    userWord.setCreateDate(new Date());
                    userWord.setUpdateDate(new Date());
                    return userWord;
                }).collect(Collectors.toList());
        userWordDAO.save(userWords);
    }

    public void syncBaseWordsAndUserWords(String username){
        int page = 0;
        List<BaseWord> baseWords = baseWordDAO.findAll();
        Set<String> userWords = new HashSet<>(userWordDAO.findBaseWordIdsByUsername(username));
        int syncedCount = 0;
        for (BaseWord baseWord: baseWords){
            String baseWordId = baseWord.getId();
            if (!userWords.contains(baseWordId)){
                UserWord userWord = new UserWord();
                userWord.setUpdateDate(new Date());
                userWord.setCreateDate(new Date());
                userWord.setBatchId(baseWord.getBatchId());
                userWord.setUsername(username);
                userWord.setWordMeaning(baseWord);
                userWordDAO.save(userWord);
                syncedCount++;
            }
        }
        log.info(String.format("%d words have been synced for username : %s", syncedCount, username));

    }
}
