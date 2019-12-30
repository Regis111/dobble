package messages.requests;

public class AmIWinnerRequest extends Request {
    private int shoutID;

    public AmIWinnerRequest(String clientID, int shoutID) {
        super(clientID);
        this.shoutID = shoutID;
    }

    public AmIWinnerRequest() {
    }

    public int getShoutID() {
        return shoutID;
    }
}
