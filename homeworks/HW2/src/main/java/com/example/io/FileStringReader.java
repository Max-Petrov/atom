package com.example.io;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class FileStringReader implements StringReader {
    private static final String FILE_NAME = "dictionary.txt";
    
    public ArrayList<String> getStrings() throws IOException {
        Path path = Paths.get(FILE_NAME);
        Scanner scanner = new Scanner(path);
        ArrayList<String> wordsList = new ArrayList<>();
        
        while (scanner.hasNextLine()) {
            wordsList.add(scanner.nextLine());
        }
        
        return wordsList;
    }
}
