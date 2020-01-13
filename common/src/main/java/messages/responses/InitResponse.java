package messages.responses;

public class InitResponse extends Response {
    private int firstCard;
    private int secondCard;

    public InitResponse(String clientID, int shoutID, int firstCard, int secondCard) {
        super(clientID, shoutID);
        this.firstCard = firstCard;
        this.secondCard = secondCard;
    }

    public int getFirstCard() {
        return firstCard;
    }

    public int getSecondCard() {
        return secondCard;
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
