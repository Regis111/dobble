package pl.dobblepolskab.services;

import gamecontent.DifficultyLevel;

public interface SessionConfigurationService {
    void setComputerPlayersNumber(int playersNumber);

    void setComputerDifficulty(DifficultyLevel computerDifficulty);

    void startGameSession();

    void endGameSession();
}
