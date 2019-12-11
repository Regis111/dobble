package pl.dobblepolskab.common.sockets.message;

public abstract class Message {
    private String sender;

    protected Message(String sender){
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }
}
