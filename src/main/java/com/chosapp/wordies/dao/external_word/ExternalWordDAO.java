package com.chosapp.wordies.dao.external_word;


import com.chosapp.wordies.dto.external_word.ExternalWord;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class ExternalWordDAO {

    private final ExternalWordRepository externalWordRepository;
    private final ModelMapper daoModelMapper;

    @Autowired
    public ExternalWordDAO(ExternalWordRepository externalWordRepository, ModelMapper daoModelMapper) {
        this.externalWordRepository = externalWordRepository;
        this.daoModelMapper = daoModelMapper;
    }


    public ExternalWord save(ExternalWord externalWord){
        ExternalWordEntity externalWordEntity = daoModelMapper.map(externalWord,ExternalWordEntity.class);
        externalWordEntity = externalWordRepository.save(externalWordEntity).block();
        return daoModelMapper.map(externalWordEntity,ExternalWord.class);
    }

    public Optional<ExternalWord> findByWordName(String wordName){
        Mono<ExternalWordEntity> externalWordEntity = externalWordRepository.findByWordName(wordName);
        externalWordEntity
                .flatMap(e -> daoModelMapper.map(externalWordEntity,ExternalWord.class))
                .switchIfEmpty(e -> {
                    ExternalWord externalWord = daoModelMapper.map(externalWordEntity,ExternalWord.class);
                    return Optional.ofNullable(externalWord);
                }).

        Optional<ExternalWord> optionalExternalWord = Optional.empty();
        if (externalWordEntity == null)
            return optionalExternalWord;
        else {
            ExternalWord externalWord = daoModelMapper.map(externalWordEntity,ExternalWord.class);
            return Optional.ofNullable(externalWord);
        }
    }

}
