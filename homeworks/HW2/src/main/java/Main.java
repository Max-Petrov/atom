import java.io.IOException;

import io.Console;

public class Main {

    public static void main(String[] args) throws IOException {
        new BullsAndCows(new Console(), 10).play();
    }
}
