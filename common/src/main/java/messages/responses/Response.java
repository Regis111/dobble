package messages.responses;

import messages.Message;

public abstract class Response extends Message {
    protected int shoutID;

    public Response() {}

    public Response(String clientID, int shoutID) {
        super(clientID);
        this.shoutID = shoutID;
    }

    public int getShoutID() {
        return shoutID;
    }

    @Override
    public String toString() {
        return "Response{" +
                ", shoutID=" + shoutID +
                ", clientID='" + clientID + '\'' +
                '}';
    }
}
