package com.chosapp.wordies.dao.base_word;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BaseWordRepository extends MongoRepository<BaseWordEntity, ObjectId> {

    Optional<BaseWordEntity> findFirstByName(String name);

    List<BaseWordEntity> findByName(String name);



    @Query("{_id:{$nin: ?0}}")
    Optional<BaseWordEntity> findFirstByNotIncludeObjectIds(List<ObjectId> ids);

}
