package pl.dobblepolskab.common.gamecontent;

import org.junit.Assert;
import org.junit.Test;

public class GameCardSymbolTest {
    private static String errorString = "Validation of game content box doesn't work!";

    @Test
    public void setImageTest(){
        GameContentBox gameContentBox = new GameContentBox("build/resources/test/testGameContentBox");
        String errorString = "Performing hash from image data doesn't work!";
        GameCardSymbol symbol1 = gameContentBox.getNextGameCardSymbol();
        GameCardSymbol symbol2 = gameContentBox.getNextGameCardSymbol();
        GameCardSymbol symbol3 = gameContentBox.getNextGameCardSymbol();
        Assert.assertNotEquals(errorString, symbol1.getSymbolId(), symbol2.getSymbolId());
        Assert.assertEquals(errorString, symbol1.getSymbolId(), symbol3.getSymbolId());
    }

}
