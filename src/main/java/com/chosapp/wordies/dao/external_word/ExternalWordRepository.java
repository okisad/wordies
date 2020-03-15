package com.chosapp.wordies.dao.external_word;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ExternalWordRepository extends ReactiveMongoRepository<ExternalWordEntity, ObjectId> {

    Mono<ExternalWordEntity> findByWordName(String wordName);

}
