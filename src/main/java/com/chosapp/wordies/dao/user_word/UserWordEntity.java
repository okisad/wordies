package com.chosapp.wordies.dao.user_word;


import com.chosapp.wordies.dao.base_word.BaseWordEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Setter
@Getter
@Document(collection = "user_words")
public class UserWordEntity {

    @Id
    private String id;
    private Date createDate;
    private Date updateDate;
    private int batchId;
    private String username;
    private int showedCount;
    private int knowCount;
    private int notKnowCount;
    private String level;
    @DBRef
    private BaseWordEntity wordMeaning;

}
