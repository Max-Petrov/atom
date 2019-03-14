package com.example;

import com.example.io.Console;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        new BullsAndCows(new Console(), 10).play();
    }
}
