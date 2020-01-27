package messages.requests;

import gamecontent.DifficultyLevel;

public class InitRequest extends Request {
    private DifficultyLevel computerDifficulty;

    private int computerPlayersNumber;

    private int gameTimeInMinutes;

    public InitRequest() {
    }

    public InitRequest(String clientID, DifficultyLevel computerDifficulty, int computerPlayersNumber, int gameTimeInMinutes) {
        super(clientID);
        this.computerDifficulty = computerDifficulty;
        this.computerPlayersNumber = computerPlayersNumber;
        this.gameTimeInMinutes = gameTimeInMinutes;
    }

    public DifficultyLevel getComputerDifficulty() {
        return computerDifficulty;
    }

    public int getComputerPlayersNumber() {
        return computerPlayersNumber;
    }

    public int getGameTimeInMinutes() {
        return gameTimeInMinutes;
    }
}
