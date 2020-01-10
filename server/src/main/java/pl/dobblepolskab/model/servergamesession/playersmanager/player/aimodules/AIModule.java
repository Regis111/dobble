package pl.dobblepolskab.model.servergamesession.playersmanager.player.aimodules;

import pl.dobblepolskab.model.server.Server;

public abstract class AIModule {
    private String serverIpAddress;
    private String moduleId;

    public AIModule (String moduleId){
        serverIpAddress = "127.0.0.1";
        this.moduleId = moduleId;
    }


}
