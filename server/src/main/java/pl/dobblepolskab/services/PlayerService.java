package pl.dobblepolskab.services;

import pl.dobblepolskab.model.servergamesession.playersmanager.player.HumanPlayer;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.Player;

import java.util.Map;

public interface PlayerService {
    Map<String, HumanPlayer> getHumanPlayers();

    Player getPlayer(String clientID);
}
