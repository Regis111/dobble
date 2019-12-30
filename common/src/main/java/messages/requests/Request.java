package messages.requests;

import messages.Message;

public abstract class Request extends Message {
    public Request(String clientID) {
        super(clientID);
    }

    public Request() {
    }
}
