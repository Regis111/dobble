package messages;

public abstract class Message {
    String clientID;
    int shoutID;

    public Message(String clientID, int shoutID) {
        this.clientID = clientID;
        this.shoutID = shoutID;
    }

    public Message() {
    }

    public String getClientID() {
        return clientID;
    }

    public int getShoutID() {
        return shoutID;
    }

    @Override
    public String toString() {
        return "Message{" +
                "clientID='" + clientID + '\'' +
                ", shoutID=" + shoutID +
                '}';
    }
}
