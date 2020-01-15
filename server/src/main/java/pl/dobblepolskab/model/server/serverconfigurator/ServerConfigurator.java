package pl.dobblepolskab.model.server.serverconfigurator;

public abstract class ServerConfigurator {
    private int serverPortId;
    private int aiIntelligenceLevel;
    private String gameContentBoxPath;
    protected boolean completed;

    private void initObject(){
        completed = false;
    }

    protected ServerConfigurator(){
        initObject();
    }

    protected void setServerPortId (int portId){
        serverPortId = portId;
    }

    protected void setAIIntelligenceLevel(int level){
        aiIntelligenceLevel = level;
    }

    protected void setGameContentBoxPath(String path){
        gameContentBoxPath = path;
    }

    public int getServerPortId() {
        return serverPortId;
    }

    public int getAiIntelligenceLevel() {
        return aiIntelligenceLevel;
    }

    public String getGameContentBoxPath(){
        return gameContentBoxPath;
    }

    public boolean isConfigurationCompleted(){
        return completed;
    }
}
