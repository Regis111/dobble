package pl.dobblepolskab.model.servergamesession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import pl.dobblepolskab.model.servergamesession.ServerGameSession;
import pl.dobblepolskab.model.servergamesession.playersmanager.PlayersManager;

import javax.validation.constraints.AssertTrue;

@Component
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
        
    }
}
