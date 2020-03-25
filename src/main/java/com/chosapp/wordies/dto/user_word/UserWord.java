package com.chosapp.wordies.dto.user_word;

import com.chosapp.wordies.dto.external_word.BaseWord;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Getter
@Setter
@ToString
public class UserWord {

    private String id;
    private Date createDate;
    private Date updateDate;
    private int batchId;
    private String username;
    private int showedCount;
    private int knowCount;
    private int notKnowCount;
    private String level;
    private BaseWord wordMeaning;

}
