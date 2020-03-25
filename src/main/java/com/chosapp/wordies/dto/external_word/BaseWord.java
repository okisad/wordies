package com.chosapp.wordies.dto.external_word;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
public class BaseWord {


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
