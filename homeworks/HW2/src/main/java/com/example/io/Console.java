package com.example.io;

import java.util.Scanner;

public class Console implements IConsole {
    private final Scanner sScanner;

    public Console() {
        sScanner = new Scanner(System.in);
    }
    
    @Override public void print(String string) {
        System.out.print(string);
    }
    
    @Override public void println(String string) {
        System.out.println(string);
    }
    
    @Override public String read() {
        return sScanner.nextLine();
    }
}
