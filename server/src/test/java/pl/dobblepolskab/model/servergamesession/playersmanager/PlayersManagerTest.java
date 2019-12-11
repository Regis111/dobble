package pl.dobblepolskab.model.servergamesession.playersmanager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.dobblepolskab.common.gamecontent.GameContent;

public class PlayersManagerTest {
    @Before
    public void initTest(){
    }

    @Test
    public void addHumanPlayerTest(){
        String errorString = "Chacking if the player is currently added to list doesn't work!";
        PlayersManager playersManager = new PlayersManager();
        Assert.assertTrue(errorString, playersManager.addHumanPlayer("1", "1"));
        Assert.assertTrue(errorString, playersManager.addHumanPlayer("2", "2"));
        Assert.assertFalse(errorString, playersManager.addHumanPlayer("1", "3"));
        Assert.assertFalse(errorString, playersManager.addHumanPlayer("3", "2"));
    }

    @Test
    public void addComputerlayerTest(){
        String errorString = "Chacking if the are 8 players in list doesn't work!";
        PlayersManager playersManager = new PlayersManager();
        Assert.assertTrue(errorString, playersManager.addComputerPlayer());
        Assert.assertTrue(errorString, playersManager.addComputerPlayer());
        Assert.assertTrue(errorString, playersManager.addComputerPlayer());
        Assert.assertTrue(errorString, playersManager.addComputerPlayer());
        Assert.assertTrue(errorString, playersManager.addComputerPlayer());
        Assert.assertTrue(errorString, playersManager.addComputerPlayer());
        Assert.assertTrue(errorString, playersManager.addComputerPlayer());
        Assert.assertTrue(errorString, playersManager.addComputerPlayer());
        Assert.assertFalse(errorString, playersManager.addComputerPlayer());
    }

    @Test
    public void preparePlayersToGameTest(){
        String errorString = "Prepare players to game method doesn't work!";
        PlayersManager playersManager = new PlayersManager();
        Assert.assertTrue(errorString, playersManager.addHumanPlayer("1", "1"));
        Assert.assertTrue(errorString, playersManager.addHumanPlayer("2", "2"));
        Assert.assertTrue(errorString, playersManager.addHumanPlayer("3", "3"));
        Assert.assertTrue(errorString, playersManager.addHumanPlayer("4", "4"));
        GameContent gameContent = new GameContent();
    }

}
