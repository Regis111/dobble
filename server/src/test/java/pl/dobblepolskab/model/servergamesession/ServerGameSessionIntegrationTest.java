package pl.dobblepolskab.model.servergamesession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.dobblepolskab.model.servergamesession.ServerGameSession;
import pl.dobblepolskab.model.servergamesession.playersmanager.PlayersManager;

import javax.validation.constraints.AssertTrue;

@Configuration
@ComponentScan(basePackages = "pl.dobblepolskab.model.servergamesession")
class SpringContext {

}

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringContext.class)
public class ServerGameSessionIntegrationTest {
    @Autowired
    ServerGameSession serverGameSession;
    @Autowired
    PlayersManager playersManager;

    @Test
    public void ServerGameSessionTest() {
        //when-1
        boolean []addHumanPlayerRetVal = {playersManager.addHumanPlayer("Human 1", "Human_1"),
                playersManager.addHumanPlayer("Human 2", "Human_2")};

        //then-1
        Assert.assertTrue("addHumanPlayer()", addHumanPlayerRetVal[0]);
        Assert.assertTrue("addHumanPlayer()", addHumanPlayerRetVal[1]);

        //when-2
        boolean []deleteHumanPlayerRetVal = {playersManager.deleteHumanPlayer("Human_1"),
                playersManager.deleteHumanPlayer("Human_1")};

        //then-2
        Assert.assertTrue("deleteHumanPlayer()", deleteHumanPlayerRetVal[0]);
        Assert.assertFalse("deleteHumanPlayer()", deleteHumanPlayerRetVal[1]);

        //when-3
        boolean []addHumanPlayerRetVal2 = {playersManager.addHumanPlayer("Human 1", "Human_1"),
                playersManager.addHumanPlayer("Human 2", "Human_2")};

        //then-3
        Assert.assertTrue("addHumanPlayer()", addHumanPlayerRetVal2[0]);
        Assert.assertFalse("addHumanPlayer()", addHumanPlayerRetVal2[1]);

        //when-4
        serverGameSession.startGameSession();
        boolean []isWinner1 = {serverGameSession.isWinner("Human_2", 1),
                serverGameSession.isWinner("Human_1", 1),
                serverGameSession.isWinner("Human_2", 1)};

        //then-4
        Assert.assertTrue("isWinner()", isWinner1[0]);
        Assert.assertFalse("isWinner()", isWinner1[1]);
        Assert.assertFalse("isWinner()", isWinner1[2]);

        //when-5
        serverGameSession.startGameSession();
        boolean []isWinner2 = {serverGameSession.isWinner("Human_2", 1),
                serverGameSession.isWinner("Human_1", 1),
                serverGameSession.isWinner("Human_1", 1)};

        //then-5
        Assert.assertFalse("isWinner()", isWinner2[0]);
        Assert.assertFalse("isWinner()", isWinner2[1]);
        Assert.assertFalse("isWinner()", isWinner2[2]);

        //when-6
        serverGameSession.startGameSession();
        boolean []isWinner3 = {serverGameSession.isWinner("Human_1", 2),
                serverGameSession.isWinner("Human_2", 3),
                serverGameSession.isWinner("Human_1", 3)};

        //then-6
        Assert.assertTrue("isWinner()", isWinner3[0]);
        Assert.assertTrue("isWinner()", isWinner3[1]);
        Assert.assertFalse("isWinner()", isWinner3[2]);
        
    }
}
