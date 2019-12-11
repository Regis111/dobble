package pl.dobblepolskab.common.gamecontent;
import org.junit.Assert;
import org.junit.Test;

public class GameContentBoxTest {
    public boolean callBindToDirectory(String boxPath)
    {
        GameContentBox gameContentBox = new GameContentBox();
        return gameContentBox.bindToDirectory(boxPath);
    }

    @Test
    public void bindToDirectoryValidTest(){
        Assert.assertTrue(callBindToDirectory("build/resources/test/testGameContentBox"));
    }

    @Test
    public void bindToDirectoryCorrupted1Test(){
        Assert.assertFalse(callBindToDirectory("build/resources/test/testGameContentBoxCorrupted1"));
    }

    @Test
    public void bindToDirectoryCorrupted2Test(){
        Assert.assertFalse(callBindToDirectory("build/resources/test/testGameContentBoxCorrupted2"));
    }

    @Test
    public void bindToDirectoryCorrupted3Test(){
        Assert.assertFalse(callBindToDirectory("build/resources/test/testGameContentBoxCorrupted3"));
    }

    @Test
    public void bindToDirectoryCorrupted4Test(){
        Assert.assertFalse(callBindToDirectory("build/resources/test/testGameContentBoxCorrupted4"));
    }
}
