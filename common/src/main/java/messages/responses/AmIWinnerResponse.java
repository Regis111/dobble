package messages.responses;

public class AmIWinnerResponse extends Response {

    private int card;

    private ResponseType responseType;

    public AmIWinnerResponse() {}

    public AmIWinnerResponse(String clientID, int shoutID, ResponseType responseType, int card) {
        super(clientID, shoutID);
        this.responseType = responseType;
        this.card = card;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public int getCard() {
        return card;
    }

    @Override
    public String toString() {
        return "AmIWinnerResponse{" +
                "card=" + card +
                ", responseType=" + responseType +
                ", shoutID=" + shoutID +
                ", clientID='" + clientID + '\'' +
                '}';
    }
}
