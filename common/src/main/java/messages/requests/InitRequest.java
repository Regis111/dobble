package messages.requests;

public class InitRequest extends Request {
    private int computerDifficulty;

    private int computerPlayersNumber;

    public InitRequest(String clientID, int computerDifficulty, int computerPlayersNumber) {
        super(clientID);
        this.computerDifficulty = computerDifficulty;
        this.computerPlayersNumber = computerPlayersNumber;
    }

    public int getComputerDifficulty() {
        return computerDifficulty;
    }

    public int getComputerPlayersNumber() {
        return computerPlayersNumber;
    }
}
