package com.chosapp.wordies.service.user_word;


import com.chosapp.wordies.dao.user_word.UserWordDAO;
import com.chosapp.wordies.dto.user_word.UserWord;
import com.chosapp.wordies.dto.user_word.UserWordCount;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class UserWordService {

    private final UserWordDAO userWordDAO;

    @Autowired
    public UserWordService(UserWordDAO userWordDAO) {
        this.userWordDAO = userWordDAO;
    }

    public List<UserWord> save(List<UserWord> userWords){
        return userWordDAO.save(userWords);
    }

    public UserWord save(UserWord userWord){
        return userWordDAO.save(userWord);
    }

    public Optional<UserWord> findNotShowedWord(){
        String username = "oktay";
        Optional<UserWord> userWord = userWordDAO.findByShowedCount(username,0);
        if (!userWord.isPresent()) log.warn(String.format("There is no any not showed word, username : %s", username));
        userWord.map(w -> {
            w.setShowedCount(1);
            userWordDAO.save(w);
            return w;
        });
        return userWord;
    }

    public UserWordCount findUserWordCount() {
        UserWordCount userWordCount = new UserWordCount();
        int totalWord = userWordDAO.findWordNumber();
        int showedCount = userWordDAO.findWordNumberMinShowedCount(0);
        int A1ShowCount = userWordDAO.findWordNumberLevelAndMinShowedCount("A1",0);
        int A2ShowCount = userWordDAO.findWordNumberLevelAndMinShowedCount("A2",0);
        int B1ShowCount = userWordDAO.findWordNumberLevelAndMinShowedCount("B1",0);
        int B2ShowCount = userWordDAO.findWordNumberLevelAndMinShowedCount("B2",0);
        int C1ShowCount = userWordDAO.findWordNumberLevelAndMinShowedCount("C1",0);
        int C2ShowCount = userWordDAO.findWordNumberLevelAndMinShowedCount("C2",0);

        userWordCount.setTotalWordCount(totalWord);
        userWordCount.setShowedWordCount(showedCount);
        userWordCount.setShowedA1Count(A1ShowCount);
        userWordCount.setShowedA2Count(A2ShowCount);
        userWordCount.setShowedB1Count(B1ShowCount);
        userWordCount.setShowedB2Count(B2ShowCount);
        userWordCount.setShowedC1Count(C1ShowCount);
        userWordCount.setShowedC2Count(C2ShowCount);

        int A1Count = userWordDAO.findWordNumberLevel("A1");
        int A2Count = userWordDAO.findWordNumberLevel("A2");
        int B1Count = userWordDAO.findWordNumberLevel("B1");
        int B2Count = userWordDAO.findWordNumberLevel("B2");
        int C1Count = userWordDAO.findWordNumberLevel("C1");
        int C2Count = userWordDAO.findWordNumberLevel("C2");

        userWordCount.setAllA1Count(A1Count);
        userWordCount.setAllA2Count(A2Count);
        userWordCount.setAllB1Count(B1Count);
        userWordCount.setAllB2Count(B2Count);
        userWordCount.setAllC1Count(C1Count);
        userWordCount.setAllC2Count(C2Count);

        return userWordCount;
    }
}
