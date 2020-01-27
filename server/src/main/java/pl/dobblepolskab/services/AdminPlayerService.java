package pl.dobblepolskab.services;

import pl.dobblepolskab.model.servergamesession.playersmanager.player.Player;

import java.util.Optional;

public interface AdminPlayerService {

    Optional<Player> addPlayerToGame(String playerName, String playerClientID);

    Optional<Player> deletePlayerFromGame(String playerClientID);
}
