package messages.requests;

public class DeletePlayerRequest extends Request {

    private String playerToDeleteID;

    public DeletePlayerRequest(String clientID, int shoutID, String playerToDeleteID) {
        super(clientID);
        this.playerToDeleteID = playerToDeleteID;
    }

    public String getPlayerToDeleteID() {
        return playerToDeleteID;
    }
}
