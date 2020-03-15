package com.chosapp.wordies.dto.external_word;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter
@Setter
@ToString
public class ExternalWordMeaning {

    private String id;
    private String action;
    private String level;
    private String description;
    private List<String> examples;
    private List<String> thesaurus;
}
