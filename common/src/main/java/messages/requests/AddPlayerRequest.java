package messages.requests;

public class AddPlayerRequest extends Request {

    private String playerToAddName;

    public AddPlayerRequest() {
    }

    public AddPlayerRequest(String clientID, String playerToAddName) {
        super(clientID);
        this.playerToAddName = playerToAddName;
    }

    public String getPlayerToAddName() {
        return playerToAddName;
    }
}
