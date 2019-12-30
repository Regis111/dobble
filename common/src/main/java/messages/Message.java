package messages;

public abstract class Message {
    protected String clientID;

    public Message(String clientID) {
        this.clientID = clientID;
    }

    public Message() {}

    public String getClientID() {
        return clientID;
    }

    @Override
    public String toString() {
        return "Message{" +
                "clientID='" + clientID + '\'' +
                '}';
    }
}
