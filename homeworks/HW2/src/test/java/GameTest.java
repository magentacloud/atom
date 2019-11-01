import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class GameTest {
    Game game = new Game();

    @Test
    public void getWordFromDictionaryTest() {
        String str = game.getWordFromDictionary();
        assertNotNull(str);
    }

    @Test
    public void countCowsTest() {
        assertEquals(game.countCows("java", "lava"), 3);
    }
}
