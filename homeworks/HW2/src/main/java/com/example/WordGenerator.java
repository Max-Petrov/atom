package com.example;

import com.example.io.FileStringReader;
import com.example.io.StringReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WordGenerator {
    
    public String getWord() throws IOException {
        StringReader stringReader = new FileStringReader();
        ArrayList<String> wordsList = new ArrayList<>();
        try {
            wordsList = stringReader.getStrings();
        } catch (Exception ex) {
            Logger.getLogger(WordGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(0, wordsList.size());

        return wordsList.get(randomIndex);
    }
}
