package com.chosapp.wordies.dao.external_word;


import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
public class ExternalWordMeaningEntity {

    private String id;
    private String action;
    private String level;
    private String description;
    private List<String> examples;
    private List<String> thesaurus;

}
