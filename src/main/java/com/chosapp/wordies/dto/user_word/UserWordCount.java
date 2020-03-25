package com.chosapp.wordies.dto.user_word;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class UserWordCount {

    private int totalWordCount;
    private int showedWordCount;
    private int showedA1Count;
    private int showedA2Count;
    private int showedB1Count;
    private int showedB2Count;
    private int showedC1Count;
    private int showedC2Count;

    private int allA1Count;
    private int allA2Count;
    private int allB1Count;
    private int allB2Count;
    private int allC1Count;
    private int allC2Count;

}
