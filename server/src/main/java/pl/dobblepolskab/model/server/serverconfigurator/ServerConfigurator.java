package pl.dobblepolskab.model.server.serverconfigurator;

public abstract class ServerConfigurator {
    private int serverPortId;
    private int aiIntelligenceLevel;
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

    public int getServerPortId() {
        return serverPortId;
    }

    public int getAiIntelligenceLevel() {
        return aiIntelligenceLevel;
    }

    public boolean isConfigurationCompleted(){
        return completed;
    }
}
