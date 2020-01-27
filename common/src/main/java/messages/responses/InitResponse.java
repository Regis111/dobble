package messages.responses;

public class InitResponse extends Response {
    private int firstCard;
    private int secondCard;
    private int gameTimeInMinutes;

    public InitResponse(){}

    public InitResponse(String clientID, int shoutID, int firstCard, int secondCard, int gameTimeInMinutes) {
        super(clientID, shoutID);
        this.firstCard = firstCard;
        this.secondCard = secondCard;
        this.gameTimeInMinutes = gameTimeInMinutes;
    }

    public int getFirstCard() {
        return firstCard;
    }

    public int getSecondCard() {
        return secondCard;
    }

    public int getGameTimeInMinutes() {
        return gameTimeInMinutes;
    }

    @Override
    public String toString() {
        return "InitResponse{" +
                "firstCard=" + firstCard +
                ", secondCard=" + secondCard +
                ", shoutID=" + shoutID +
                ", clientID='" + clientID + '\'' +
                '}';
    }
}
