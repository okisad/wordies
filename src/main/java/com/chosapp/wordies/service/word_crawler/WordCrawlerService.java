package com.chosapp.wordies.service.word_crawler;

import com.chosapp.wordies.dto.external_word.ExternalWord;
import com.chosapp.wordies.dto.external_word.ExternalWordMeaning;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class WordCrawlerService {

    public static final String URL = "https://dictionary.cambridge.org/dictionary/english/";


    public ExternalWord getWord(String word) throws IOException {
        Document document = Jsoup.connect(URL + word).get();
        Elements elements = document.select(".entry-body__el");
        ExternalWord externalWord = new ExternalWord();
        List<ExternalWordMeaning> externalWordMeanings = new ArrayList<>();
        externalWord.setMeanings(externalWordMeanings);
        for (Element element : elements){
            String title = element.select(".headword .hw").html();
            externalWord.setWordName(title);
            String level = element.select(".headword .hw").html();
            String def = element.select(".def").text();
            String action = element.select(".pos.dpos").text();
            List<String> examples = new ArrayList<>();
            Elements exampleElements = element.select(".examp.dexamp");
            for (Element e : exampleElements){
                examples.add(e.text());
            }
            List<String> thesauruses = new ArrayList<>();
            Elements tElements = element.select(".daccord_lb li .haf");
            for (Element e : tElements){
                thesauruses.add(e.text());
            }
            ExternalWordMeaning externalWordMeaning = new ExternalWordMeaning();
            externalWordMeaning.setAction(action);
            externalWordMeaning.setDescription(def);
            externalWordMeaning.setLevel(level);
            externalWordMeaning.setThesaurus(thesauruses);
            externalWordMeaning.setExamples(examples);
            externalWordMeanings.add(externalWordMeaning);
        }
        return externalWord;
    }

}
