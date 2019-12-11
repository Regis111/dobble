package pl.dobblepolskab.model.servergamesession.playersmanager.player;

import pl.dobblepolskab.common.gamecontent.GameCard;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.aimodules.AIModule;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.aimodules.PrimitiveAIModule;

public class ComputerPlayer extends Player{
    private AIModule aiModule;

    private AIModule getAiModuleBasicOnLevel(int level){
        switch (level){
            case 1:
                return new PrimitiveAIModule();
            default:
                return null;
        }
    }

    public ComputerPlayer(String name, String clientId, GameCard firstCard, int intelligenceLevel){
        super(name, clientId, firstCard);
        aiModule = getAiModuleBasicOnLevel(intelligenceLevel);
    }
}
