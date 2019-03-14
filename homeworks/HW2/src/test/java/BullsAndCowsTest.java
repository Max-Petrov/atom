import com.example.BullsAndCows;
import com.example.io.IConsole;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BullsAndCowsTest {
    
    private final BullsAndCows mGame = new BullsAndCows(
            new IConsole() {
                public void print(String string) {
                    // ignore.
                }

                public void println(String string) {
                    // ignore.
                }

                public String read() {
                    return "";
                }
            }, 
            10);
    
    @Test
    public void TestBullsAndCows1() {
        BullsAndCows.Result result = mGame.getBullsAndCowsResult("aabbccd", "aabbert");
        assertThat(result.bulls, is(equalTo(4)));
        assertThat(result.cows, is(equalTo(0)));
    }

    @Test
    public void TestBullsAndCows2() {
        BullsAndCows.Result result = mGame.getBullsAndCowsResult("wefsfef", "rtaokl");
        assertThat(result.bulls, is(equalTo(0)));
        assertThat(result.cows, is(equalTo(0)));
    }

    @Test
    public void TestBullsAndCows3() {
        BullsAndCows.Result result = mGame.getBullsAndCowsResult("abcdef", "acfedl");
        assertThat(result.bulls, is(equalTo(1)));
        assertThat(result.cows, is(equalTo(4)));
    }
}
