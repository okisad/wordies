package com.chosapp.wordies.dao.user_word;

import com.chosapp.wordies.dto.external_word.BaseWord;
import com.chosapp.wordies.dto.user_word.UserWord;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserWordDAO {

    private final UserWordEntityRepository userWordEntityRepository;
    private final ModelMapper daoModelMapper;

    @Autowired
    public UserWordDAO(UserWordEntityRepository userWordEntityRepository, ModelMapper daoModelMapper) {
        this.userWordEntityRepository = userWordEntityRepository;
        this.daoModelMapper = daoModelMapper;
    }

    public UserWord save(UserWord userWord) {
        UserWordEntity userWordEntity = daoModelMapper.map(userWord, UserWordEntity.class);
        userWordEntity = userWordEntityRepository.save(userWordEntity);
        return daoModelMapper.map(userWordEntity, UserWord.class);
    }

    public List<UserWord> save(List<UserWord> userWords) {
        List<UserWordEntity> userWordEntities = userWords.stream().map(w -> daoModelMapper.map(w, UserWordEntity.class)).collect(Collectors.toList());
        userWordEntities = userWordEntityRepository.saveAll(userWordEntities);
        return userWordEntities
                .stream()
                .map(w -> daoModelMapper.map(w, UserWord.class))
                .collect(Collectors.toList());
    }

    public List<UserWord> findAll(){
        List<UserWordEntity> userWordEntities = userWordEntityRepository.findAll();
        return userWordEntities.stream()
                .map(w -> daoModelMapper.map(w, UserWord.class))
                .collect(Collectors.toList());
    }

    public List<UserWord> findByUsername(String username) {
        List<UserWordEntity> userWordEntities = userWordEntityRepository.findAll();
        return userWordEntities
                .stream()
                .map(w -> daoModelMapper.map(w, UserWord.class))
                .collect(Collectors.toList());
    }

    public List<String> findBaseWordIdsByUsername(String username) {
        List<UserWordEntity> userWordEntities = userWordEntityRepository.findAll();
        return userWordEntities
                .stream()
                .map(w -> w.getWordMeaning().getId())
                .collect(Collectors.toList());
    }

    public Optional<UserWord> findByShowedCount(String username,int showedCount){
        Optional<UserWordEntity> userWordEntity = userWordEntityRepository.findFirstByUsernameAndShowedCount(username, showedCount);
        return userWordEntity.map(w -> daoModelMapper.map(w, UserWord.class));
    }

    public int findWordNumber(){
        return (int) userWordEntityRepository.count();
    }

    public int findWordNumberMinShowedCount(int minShowedCount){
        return (int) userWordEntityRepository.countByShowedCountGreaterThan(minShowedCount);
    }

    public int findWordNumberLevelAndMinShowedCount(String level,int minShowedCount){
        return (int) userWordEntityRepository.countByLevelAndShowedCountGreaterThan(level,minShowedCount);
    }

    public int findWordNumberLevel(String level){
        return (int) userWordEntityRepository.countByLevel(level);
    }
}
