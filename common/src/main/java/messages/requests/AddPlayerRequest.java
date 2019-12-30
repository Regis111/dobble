package messages.requests;

public class AddPlayerRequest extends Request {
    private String playerToAddID;
    private String playerToAddName;

    public AddPlayerRequest(String clientID, String playerToAddID, String playerToAddName) {
        super(clientID);
        this.playerToAddID = playerToAddID;
        this.playerToAddName = playerToAddName;
    }

    public String getPlayerToAddID() {
        return playerToAddID;
    }

    public String getPlayerToAddName() {
        return playerToAddName;
    }
}
