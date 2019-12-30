package messages.responses;

import messages.Pair;
import messages.requests.Request;

public class AmIWinnerResponse extends Request {

    private int shoutID;

    private ResponseType responseType;

    private Pair pair;

    public Pair getPair() {
        return pair;
    }

    public AmIWinnerResponse(String clientID, int shoutID, ResponseType responseType, Pair pair) {
        super(clientID);
        this.responseType = responseType;
        this.shoutID = shoutID;
        this.pair = pair;
    }

    public int getShoutID() {
        return shoutID;
    }

    public ResponseType getResponseType() {
        return responseType;
    }
}
