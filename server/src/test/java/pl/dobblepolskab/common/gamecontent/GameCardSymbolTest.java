package pl.dobblepolskab.common.gamecontent;

import org.junit.Assert;
import org.junit.Test;

public class GameCardSymbolTest {
    private static String errorString = "Validation of game content box doesn't work!";

    @Test
    public void setImageTest(){
        // given
        GameContentBox gameContentBox = new GameContentBox("build/resources/test/testGameContentBox");
        String errorString = "Performing hash from image data doesn't work!";
        GameCardSymbol symbol1 = gameContentBox.getNextGameCardSymbol();
        GameCardSymbol symbol2 = gameContentBox.getNextGameCardSymbol();
        GameCardSymbol symbol3 = gameContentBox.getNextGameCardSymbol();

        // when
        int symbol1Id = symbol1.getSymbolId();
        int symbol2Id = symbol2.getSymbolId();
        int symbol3Id = symbol3.getSymbolId();

        // then
        Assert.assertNotEquals(errorString, symbol1Id, symbol2Id);
        Assert.assertEquals(errorString, symbol1Id, symbol3Id);
    }

}
