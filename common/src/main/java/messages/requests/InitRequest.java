package messages.requests;

import gamecontent.DifficultyLevel;

public class InitRequest extends Request {
    private DifficultyLevel computerDifficulty;

    private int computerPlayersNumber;

    public InitRequest(String clientID, DifficultyLevel computerDifficulty, int computerPlayersNumber) {
        super(clientID);
        this.computerDifficulty = computerDifficulty;
        this.computerPlayersNumber = computerPlayersNumber;
    }

    public DifficultyLevel getComputerDifficulty() {
        return computerDifficulty;
    }

    public int getComputerPlayersNumber() {
        return computerPlayersNumber;
    }
}
