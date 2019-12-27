package pl.dobblepolskab.services;

public interface SessionConfigurationService {
    void setComputerPlayersNumber(int playersNumber);

    void setComputerDifficulty(int computerDifficulty);

    void startGameSession();

    void endGameSession();
}
