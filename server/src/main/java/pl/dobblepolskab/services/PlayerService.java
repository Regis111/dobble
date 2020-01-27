package pl.dobblepolskab.services;

import pl.dobblepolskab.model.servergamesession.playersmanager.player.HumanPlayer;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.Player;

import java.util.Map;
import java.util.Optional;

public interface PlayerService {
    Map<String, HumanPlayer> getHumanPlayers();

    Optional<Player> getPlayer(String clientID);
}
