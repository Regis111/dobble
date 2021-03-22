package pl.dobblepolskab.model.servergamesession.playersmanager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import gamecontent.GameContent;

public class PlayersManagerTest {
    private GameContent gameContent = GameContent.getInstance();

    @Test
    @Ignore
    public void addHumanPlayerTest() {
        // given
        String errorString = "Chacking if the player is currently added to list doesn't work!";
        PlayersManager playersManager = new PlayersManager();

        // when
        boolean[] addsPassed = new boolean[]{
                playersManager.addHumanPlayer("1", "1"),
                playersManager.addHumanPlayer("2", "2"),
                playersManager.addHumanPlayer("1", "3"),
                playersManager.addHumanPlayer("3", "2")
        };

        // then
        Assert.assertTrue(errorString, addsPassed[0]);
        Assert.assertTrue(errorString, addsPassed[1]);
        Assert.assertFalse(errorString, addsPassed[2]);
        Assert.assertFalse(errorString, addsPassed[3]);
    }

    @Test
    public void addComputerlayerTest() {
        // given
        String errorString = "Chacking if the are 8 players in list doesn't work!";
        PlayersManager playersManager = new PlayersManager();

        // when
        boolean[] addsPassed = new boolean[9];
        for (int i = 0; i < 9; i++)
            addsPassed[i] = playersManager.addComputerPlayer();

        // then
        for (int i = 0; i < 8; i++)
            Assert.assertTrue(errorString, addsPassed[i]);
        Assert.assertFalse(errorString, addsPassed[8]);
    }

//    @Test
//    public void preparePlayersToGameTest() {
//        // given
//        String errorString = "Prepare players to game method doesn't work!";
//        PlayersManager playersManager = new PlayersManager();
//        Assert.assertTrue(errorString, playersManager.addHumanPlayer("1", "1"));
//        Assert.assertTrue(errorString, playersManager.addHumanPlayer("2", "2"));
//        Assert.assertTrue(errorString, playersManager.addHumanPlayer("3", "3"));
//        Assert.assertTrue(errorString, playersManager.addHumanPlayer("4", "4"));
//        GameContent gameContent = new GameContent();
//        // .....
//    }

}
