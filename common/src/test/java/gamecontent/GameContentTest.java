package gamecontent;
import org.junit.Assert;
import org.junit.Test;

public class GameContentTest {

    @Test
    public void addGameCardTest(){
        // given
        int[] defArray = new int[] {1, 2, 3, 4, 5, 6, 7, 8};
        GameCard gameCard1 = new GameCard(defArray);
        defArray[5] = 9;
        GameCard gameCard2 = new GameCard(defArray);
        GameContent gameContent = GameContent.getInstance();
        String errorString = "Chacking if the same card (THE SAME SEQUENCE ONLY) already exists in the game content doesn't work!";

        // when
        boolean firstAddPassed = gameContent.addGameCard(gameCard1);
        boolean secondAddPassed = gameContent.addGameCard(gameCard2);
        boolean thirdAddPassed = gameContent.addGameCard(gameCard2);

        // then
        Assert.assertTrue(errorString, firstAddPassed);
        Assert.assertTrue(errorString, secondAddPassed);
        Assert.assertFalse(errorString, thirdAddPassed);
    }

    @Test
    public void importGameContentTest(){
        // given
        GameContentBox gameContentBox = new GameContentBox("build/resources/test/testGameContentBox");
        GameContent gameContent = GameContent.getInstance();
        String errorString = "Chacking if the same graphics already exists in the game content doesn't work!";

        // when
        boolean firstAddPassed = gameContent.addGameCardSymbol(gameContentBox.getNextGameCardSymbol().get());
        boolean secondAddPassed = gameContent.addGameCardSymbol(gameContentBox.getNextGameCardSymbol().get());
        boolean thirdAddPassed = gameContent.addGameCardSymbol(gameContentBox.getNextGameCardSymbol().get());

        //then
        Assert.assertTrue(errorString, firstAddPassed);
        Assert.assertTrue(errorString, secondAddPassed);
        Assert.assertFalse(errorString, thirdAddPassed);
    }
}
