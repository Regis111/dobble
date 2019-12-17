package pl.dobblepolskab.model.servergamesession.playersmanager.player;

import pl.dobblepolskab.common.gamecontent.GameCard;
import pl.dobblepolskab.common.gamecontent.GameContent;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.aimodules.AIModule;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.aimodules.PrimitiveAIModule;

public class ComputerPlayer extends Player{
    private AIModule aiModule;

    public ComputerPlayer(GameContent gameContent, String name, String clientId, int intelligenceLevel){
        super(gameContent, name, clientId);
        aiModule = createAiModule(clientId, intelligenceLevel);
    }

    private AIModule createAiModule(String moduleId, int level){
        switch (level){
            case 1:
                return new PrimitiveAIModule(moduleId);
            default:
                return null;
        }
    }
}
