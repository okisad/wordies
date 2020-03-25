package com.chosapp.wordies.dao.base_word;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document(collection = "base_words")
public class BaseWordEntity {

    @Id
    private String id;
    private String name;
    private String partsOfSpeech;
    private String level;
    private String description;
    private String otherMeaning;
    private List<String> examples;
    private List<String> thesaurus;
    private int batchId;
    private Date createDate;

}
