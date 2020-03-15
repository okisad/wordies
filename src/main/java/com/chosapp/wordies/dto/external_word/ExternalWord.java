package com.chosapp.wordies.dto.external_word;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
@ToString
public class ExternalWord {

    private String id;
    private String wordName;
    private List<ExternalWordMeaning> meanings;

}
