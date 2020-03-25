package com.chosapp.wordies;

import com.chosapp.wordies.dao.user_word.UserWordDAO;
import com.chosapp.wordies.dao.base_word.BaseWordDAO;
import com.chosapp.wordies.dto.user_word.UserWord;
import com.chosapp.wordies.service.word.WordService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class WordiesApplication implements CommandLineRunner {

    @Autowired
    WordService wordService;

    @Autowired
    BaseWordDAO externalWordDAO;

    @Autowired
    UserWordDAO userWordDAO;

    public static void main(String[] args) {
        SpringApplication.run(WordiesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }

    private void saveUserWord() {
        userWordDAO.findAll();
    }

    /*private void saveWordMeanings() {
        List<ExternalWord> externalWords = externalWordDAO.findAll();
        AtomicInteger i = new AtomicInteger(1);
        for (ExternalWord externalWord : externalWords) {
            externalWord.getMeanings().forEach(w -> {
                System.out.println(i.getAndIncrement());
                w.setName(externalWord.getWordName());
                externalWordDAO.save(w);
            });
        }
    }*/

    private void fetchList() throws IOException {
        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("words.txt")).getFile());
        List<String> lines = FileUtils.readLines(file, "UTF-8");
        List<String> words = new ArrayList<>();
        for (String line : lines) {
            words.add(line.split("\\s+")[1]);
        }
        int i = 1;
        for (String w : words) {
            System.out.println(i++ + "/" + words.size());
            wordService.findWord(w);

        }
    }
}
