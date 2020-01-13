package pl.dobblepolskab.model.servergamesession.playersmanager.player;

import gamecontent.DifficultyLevel;
import gamecontent.GameCard;
import gamecontent.GameContent;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.aimodules.AIModule;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.aimodules.PrimitiveAIModule;

public class ComputerPlayer extends Player{
    private AIModule aiModule;

    public ComputerPlayer(String name, String clientId, DifficultyLevel intelligenceLevel){
        super(name, clientId);
        aiModule = createAiModule(clientId, intelligenceLevel);
    }

    private AIModule createAiModule(String moduleId, DifficultyLevel level){
        switch (level){
            case Easy:
                return new PrimitiveAIModule(moduleId);
            default:
                return null;
        }
    }
}
