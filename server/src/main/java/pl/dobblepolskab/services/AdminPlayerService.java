package pl.dobblepolskab.services;

import pl.dobblepolskab.model.servergamesession.playersmanager.player.Player;

public interface AdminPlayerService {

    Player addPlayerToGame(String playerName, String playerClientID);

    Player deletePlayerFromGame(String playerClientID);
}
