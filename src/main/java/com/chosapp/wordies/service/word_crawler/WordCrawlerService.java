package com.chosapp.wordies.service.word_crawler;

import com.chosapp.wordies.dto.external_word.BaseWord;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class WordCrawlerService {

    private static final String MEANINGS_SELECTOR = ".entry-body > .pr.entry-body__el .pr.dsense";
    private static final String SUB_MEANINGS_SELECTOR = ".def-block.ddef_block";
    private static final String OTHER_MEANING_SELECTOR = ".dsense_h .guideword > span";
    private static final String TITLE_SELECTOR = " .hw.dsense_hw";
    private static final String LEVEL_SELECTOR = ".ddef_h .def-info .dxref";
    private static final String PART_OF_SPEECH_SELECTOR = ".dsense_h > .pos.dsense_pos";
    private static final String ALTERNATIVE_PART_OF_SPEECH = ".pos-header .posgram .pos:first-child";
    private static final String DESCRIPTION_SELECTOR = ".ddef_h>.def";
    private static final String EXAMPLES_SELECTOR = ".examp.dexamp";
    private static final String THESAURUS_SELECTOR = ".daccord_lb li .haf";

    public static final String URL = "https://dictionary.cambridge.org/dictionary/english/";


    public List<BaseWord> getWord(String word) throws IOException {
        try {
            Document document = Jsoup.connect(URL + word).followRedirects(true).get();
            Elements elements = document.select(MEANINGS_SELECTOR);
            if (elements.size() == 0)
                log.warn(String.format("%s is not found in cambridge.", word));
            return createExternalWord(word,elements);
        } catch (IOException e) {
            log.warn("Connection problem occured at cambridge.");
            throw e;
        }
    }

    private List<BaseWord> createExternalWord(String inputWordName, Elements elements){
        List<BaseWord> baseWords = new ArrayList<>();
        for (Element element : elements) {
            String wordName = getHTML(element,TITLE_SELECTOR);
            if (StringUtils.isEmpty(wordName)) wordName = inputWordName;
            String partOfSpeech = getHTML(element, PART_OF_SPEECH_SELECTOR);
            if (StringUtils.isEmpty(partOfSpeech)){
                partOfSpeech = getText(element.parent().parent(), ALTERNATIVE_PART_OF_SPEECH);
            }
            String otherMeaning = getHTML(element, OTHER_MEANING_SELECTOR);
            Elements subSections = element.select(SUB_MEANINGS_SELECTOR);
            List<String> thesauruses = getListOfElements(element,THESAURUS_SELECTOR);
            for (Element subElement : subSections){
                BaseWord baseWord = generateBaseWord(subElement,wordName,partOfSpeech,otherMeaning,thesauruses);
                if (!StringUtils.isEmpty(baseWord.getLevel()))
                    baseWords.add(baseWord);
            }
        }
        return baseWords;
    }

    private BaseWord generateBaseWord( Element subElement , String wordName,String partOfSpeech, String otherMeaning,List<String> thesauruses){
        return BaseWord.builder()
                .name(wordName)
                .createDate(new Date())
                .partsOfSpeech(partOfSpeech)
                .otherMeaning(otherMeaning)
                .level(getHTML(subElement,LEVEL_SELECTOR))
                .description(getText(subElement,DESCRIPTION_SELECTOR).replaceAll(":",""))
                .thesaurus(thesauruses)
                .examples(getListOfElements(subElement,EXAMPLES_SELECTOR))
                .build();
    }

    private List<String> getListOfElements(Element element,String selector){
        Elements elements = element.select(selector);
        return elements.stream().map(Element::text).collect(Collectors.toList());
    }

    private String getHTML(Element element,String selector){
        return element.select(selector).html();
    }

    private String getText(Element element,String selector){
        return element.select(selector).text();
    }

}
