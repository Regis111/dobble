package pl.dobblepolskab.model.server.serverconfigurator;

public abstract class ServerConfigurator {
    private String serverIpAddress;
    private int serverPortId;
    private int aiIntelligenceLevel;
    protected ServerConfigurator(){

    }

    protected void setServerIpAddress (String ipAddress){
        serverIpAddress = ipAddress;
    }

    protected void setServerPortId (int portId){
        serverPortId = portId;
    }

    protected void setAIIntelligenceLevel(int level){
        aiIntelligenceLevel = level;
    }

    public String getServerIpAddress() {
        return serverIpAddress;
    }

    public int getServerPortId() {
        return serverPortId;
    }

    public int getAiIntelligenceLevel() {
        return aiIntelligenceLevel;
    }
}
