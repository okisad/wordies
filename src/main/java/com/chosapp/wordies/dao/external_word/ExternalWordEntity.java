package com.chosapp.wordies.dao.external_word;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "external_words")
@Getter
@Setter
public class ExternalWordEntity {

    @Id
    private String id;
    private String wordName;
    private List<ExternalWordMeaningEntity> meanings;

}
