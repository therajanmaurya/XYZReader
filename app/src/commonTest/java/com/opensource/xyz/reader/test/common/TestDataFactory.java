package com.opensource.xyz.reader.test.common;

import com.opensource.xyz.reader.data.model.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class that makes instances of data models with random field values.
 * The aim of this class is to help setting up test fixtures.
 */
public class TestDataFactory {

    public static Article createArticle() {
        return Article.create(makeArticle());
    }

    public static List<Article> makeListArticles(int number) {
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            articles.add(createArticle());
        }
        return articles;
    }

    public static Article makeArticle() {
        return Article.builder()
                .setId(1)
                .setPhoto("https://dl.dropboxusercontent.com/u/231329/xyzreader_data/images/p004.jpg")
                .setThumb("https://dl.dropboxusercontent.com/u/231329/xyzreader_data/thumbs/p004.jpg")
                .setAspectRatio(Float.valueOf("1.49925"))
                .setAuthor("Carl Sagan")
                .setTitle("Mysteries of the Universe Solved")
                .setBody("Paroxysm of global economics <a href='http://www.google.com'>Google Search</a> event take root and flourish, realm of the galaxies take root and flourish light years, circumnavigated Tunguska event Vangelis. Realm of the galaxies as a patch of light extraplanetary?<br><br>The carbon in our apple pies hundreds of thousands of brilliant syntheses cosmic ocean Hypatia explorations across the centuries take root and flourish muse about with pretty stories for which there's little good evidence. Tunguska event birth billions upon billions venture tesseract billions upon billions! Muse about dream of the mind's eye! Radio telescope. The only home we've ever known with pretty stories for which there's little good evidence! Hydrogen atoms cosmic fugue brain is the seed of intelligence the only home we've ever known? Inconspicuous motes of rock and gas of brilliant syntheses.<br><br>Network of wormholes across the centuries Jean-François Champollion hearts of the stars? Vastness is bearable only through love, a still more glorious dawn awaits worldlets the carbon in our apple pies worldlets citizens of distant epochs corpus callosum quasar ship of the imagination. Colonies something incredible is waiting to be known from which we spring billions upon billions, paroxysm of global death with pretty stories for which there's little good evidence, intelligent beings astonishment.<br><br>Brain is the seed of intelligence, billions upon billions, corpus callosum trillion stirred by starlight consciousness cosmic fugue dispassionate extraterrestrial observer.<br><br>Bits of moving fluff. Muse about Apollonius of Perga worldlets the only home we've ever known dispassionate extraterrestrial observer with pretty stories for which there's little good evidence venture at the edge of forever, laws of physics muse about.<br><br>Photos courtesy of <a href='https://unsplash.com/'>Unsplash.com</a>.")
                .setPublishedDate("2013-06-20T00:00:00.000Z")
                .build();
    }
}