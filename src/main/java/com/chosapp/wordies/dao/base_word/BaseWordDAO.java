package com.chosapp.wordies.dao.base_word;


import com.chosapp.wordies.dto.external_word.BaseWord;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class BaseWordDAO {

    private final BaseWordRepository baseWordRepository;
    private final ModelMapper daoModelMapper;


    @Autowired
    public BaseWordDAO(BaseWordRepository baseWordRepository, ModelMapper daoModelMapper) {
        this.baseWordRepository = baseWordRepository;
        this.daoModelMapper = daoModelMapper;
    }


    public BaseWord save(BaseWord baseWord) {
        BaseWordEntity baseWordEntity = daoModelMapper.map(baseWord, BaseWordEntity.class);
        baseWordEntity = baseWordRepository.save(baseWordEntity);
        log.info(String.format("%s has been saved", baseWord.getName()));
        return daoModelMapper.map(baseWordEntity, BaseWord.class);
    }


    public List<BaseWord> save(List<BaseWord> baseWords) {
        List<BaseWordEntity> baseWordEntities = baseWords
                .stream()
                .map(w -> daoModelMapper.map(w, BaseWordEntity.class))
                .collect(Collectors.toList());
        baseWordEntities = baseWordRepository.saveAll(baseWordEntities);
        if (!CollectionUtils.isEmpty(baseWordEntities))
            log.info(String.format("Base Word : %s has been saved", baseWordEntities.get(0).getName()));
        return baseWordEntities
                .stream()
                .map(w -> daoModelMapper.map(w, BaseWord.class))
                .collect(Collectors.toList());
    }

    public Optional<BaseWord> findFirstWordMeaningByName(String name) {
        Optional<BaseWordEntity> externalWordMeaningEntity = baseWordRepository.findFirstByName(name);
        return externalWordMeaningEntity.map(e -> daoModelMapper.map(e, BaseWord.class));
    }

    public List<BaseWord> findBaseWordsByName(String name) {
        List<BaseWordEntity> baseWordEntities = baseWordRepository.findByName(name);
        return baseWordEntities
                .stream()
                .map(e -> daoModelMapper.map(e, BaseWord.class))
                .collect(Collectors.toList());
    }

    public List<BaseWord> findAll() {
        int page = 0;
        Page<BaseWordEntity> wordEntityPage = baseWordRepository.findAll(PageRequest.of(page++,100));
        List<BaseWordEntity> baseWordEntities = new ArrayList<>(wordEntityPage.getContent());
        while (!wordEntityPage.isLast()){
            log.info(String.format("user words are fetching %d/%d", page, wordEntityPage.getTotalPages()));
            wordEntityPage = baseWordRepository.findAll(PageRequest.of(page++,100));
            baseWordEntities.addAll(wordEntityPage.getContent());
        }
        return baseWordEntities
                .stream()
                .map(e -> daoModelMapper.map(e, BaseWord.class))
                .collect(Collectors.toList());
    }


    public List<BaseWord> findWithPage(int page, int size) {
        Page<BaseWordEntity> baseWordEntities = baseWordRepository.findAll(PageRequest.of(0,0));
        return baseWordEntities
                .getContent()
                .stream()
                .map(e -> daoModelMapper.map(e, BaseWord.class))
                .collect(Collectors.toList());
    }
}
