package pl.dobblepolskab.services;

import messages.Pair;

public interface GameService {
    boolean isWinner(String clientID, int shoutID);

    Pair getNextTurnState(String clientID);
}
