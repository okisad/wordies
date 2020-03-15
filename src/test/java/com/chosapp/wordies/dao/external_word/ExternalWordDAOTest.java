package com.chosapp.wordies.dao.external_word;

import com.chosapp.wordies.dto.external_word.ExternalWord;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ExternalWordDAOTest {

    @Autowired
    ExternalWordDAO externalWordDAO;

    @Test
    void save() {
        ExternalWord externalWord = new ExternalWord();
        externalWord.setWordName("deneme");
        ExternalWord savedExternalWord = externalWordDAO.save(externalWord);
        Assert.assertEquals("deneme",savedExternalWord.getWordName());
        Assert.assertNotNull(savedExternalWord.getId());
        System.out.println(savedExternalWord.getId());
    }
}