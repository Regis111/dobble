package pl.dobblepolskab.common.gamecontent;
import org.junit.Assert;
import org.junit.Test;

public class GameContentTest {

    @Test
    public void addGameCardTest(){
        int[] defArray = new int[] {1, 2, 3, 4, 5, 6, 7, 8};
        GameCard gameCard1 = new GameCard(defArray);
        defArray[5] = 9;
        GameCard gameCard2 = new GameCard(defArray);
        GameContent gameContent = new GameContent();
        String errorString = "Chacking if the same card (THE SAME SEQUENCE ONLY) already exists in the game content doesn't work!";
        Assert.assertTrue(errorString, gameContent.addGameCard(gameCard1));
        Assert.assertTrue(errorString, gameContent.addGameCard(gameCard2));
        Assert.assertFalse(errorString, gameContent.addGameCard(gameCard2));
    }

    @Test
    public void importGameContentTest(){
        GameContentBox gameContentBox = new GameContentBox("build/resources/test/testGameContentBox");
        GameContent gameContent = new GameContent();
        String errorString = "Chacking if the same graphics already exists in the game content doesn't work!";
        Assert.assertTrue(errorString, gameContent.addGameCardSymbol(gameContentBox.getNextGameCardSymbol()));
        Assert.assertTrue(errorString, gameContent.addGameCardSymbol(gameContentBox.getNextGameCardSymbol()));
        Assert.assertFalse(errorString, gameContent.addGameCardSymbol(gameContentBox.getNextGameCardSymbol()));
    }
}
