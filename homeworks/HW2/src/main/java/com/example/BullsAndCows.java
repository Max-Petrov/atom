package com.example;

import java.io.IOException;

import com.example.io.IConsole;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class BullsAndCows {
    private static final Logger LOGGER = LogManager.getLogger(BullsAndCows.class);

    private int mAttempts;

    private final IConsole mConsole;
    
    public BullsAndCows(IConsole console, int attempts) {
        mConsole = console;
        mAttempts = attempts;
    }
    
    private String getSecret() {
        try {
            return new WordGenerator().getWord();
        } catch (IOException ex) {
            return null;
        }
    }
    
    public Result getBullsAndCowsResult(String secret, String guess) {
        StringBuilder secretWord = new StringBuilder(secret);
        StringBuilder guessWord = new StringBuilder(guess);
        
        int bulls = getBulls(secretWord, guessWord);
        int cows = getCows(secretWord, guessWord);
        
        return new Result(cows, bulls);
    }
    
    // WARNING! Modifies input params. Must be called before #getCows method.
    private int getBulls(StringBuilder secretWord, StringBuilder guessWord) {
        int bulls = 0;
        
        for (int guessIndex = 0; guessIndex < guessWord.length(); guessIndex++) {
            if (guessIndex < secretWord.length() && guessWord.charAt(guessIndex) == secretWord.charAt(guessIndex)) {
                bulls++;
                guessWord.deleteCharAt(guessIndex);
                secretWord.deleteCharAt(guessIndex);
                guessIndex--;
            }
        }
        
        return bulls;
    }

    // WARNING! Modifies input params. Must be called after #getBulls method.
    private int getCows(StringBuilder secretWord, StringBuilder guessWord) {
        int cows = 0;
                
        for (int guessIndex = 0; guessIndex < guessWord.length(); guessIndex++) {
            for (int secretIndex = 0; secretIndex < secretWord.length(); secretIndex++) {
                if (guessWord.charAt(guessIndex) == secretWord.charAt(secretIndex)) {
                    cows++;
                    guessWord.deleteCharAt(guessIndex);
                    secretWord.deleteCharAt(secretIndex);
                    guessIndex--;
                    break;
                }
            }
        }
        
        return cows;
    }
    
    public void play() {
        LOGGER.info("Game started");
        mConsole.println("Welcome to 'Bulls And Cows' Game!");
        do {
            String secret = getSecret();
            LOGGER.info("The secret is " + secret);
            boolean isVictory = false;
           
            for (int i = 0; i < mAttempts; i++) {
                mConsole.println("Guess a word! Please, enter your variant: ");
                String variant = mConsole.read();
                LOGGER.info("The user entered a variant: " + variant);
                if (variant.equals(secret)) {
                    mConsole.println("You Won!!!");
                    LOGGER.info("The user won!");
                    isVictory = true;
                    break;
                } else {
                    Result result = getBullsAndCowsResult(secret, variant);
                    mConsole.println(String.format("You didn't guess! Bulls = %d and Cows = %d",
                            result.bulls, result.cows));
                }
            }
           
            if (!isVictory) {
                mConsole.println("You Lost!!!");
                LOGGER.info("The user lost");
            }
           
            mConsole.println("Do you want to start a new game?");
        } while (mConsole.read().equals("Yes"));
        
    }
    
    public static class Result {
        public final int cows;
        public final int bulls;

        public Result(int cows, int bulls) {
            this.cows = cows;
            this.bulls = bulls;
        }
    }
}
