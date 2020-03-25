package com.chosapp.wordies.dao.user_word;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserWordEntityRepository extends MongoRepository<UserWordEntity, ObjectId> {

    Optional<UserWordEntity> findFirstByUsernameAndShowedCount(String username, int showedCount);

    long countByShowedCountGreaterThan(int minShowedCount);

    long countByLevelAndShowedCountGreaterThan(String level,int minShowedCount);

    long countByLevel(String level);
}
