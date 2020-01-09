package pl.dobblepolskab.controller;

import messages.Pair;
import messages.requests.*;
import messages.responses.AmIWinnerResponse;
import messages.responses.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.HumanPlayer;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.Player;
import pl.dobblepolskab.services.*;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Controller
public class MainController {

    private final CommunicationService communicationService;

    private final PlayerService playerService;

    private final AdminPlayerService adminPlayerService;

    private final GameService gameService;

    private final SessionConfigurationService configurationService;

    @Autowired
    public MainController(CommunicationService communicationService,
                          PlayerService playerService,
                          GameService gameService,
                          AdminPlayerService adminPlayerService,
                          SessionConfigurationService sessionConfigurationService) {
        this.communicationService = communicationService;
        this.playerService = playerService;
        this.gameService = gameService;
        this.adminPlayerService = adminPlayerService;
        this.configurationService = sessionConfigurationService;
    }

    @SubscribeMapping("/dobblePair")
    public String helloFromDobble() {
        String[] winOrLose = {"WIN", "LOSE"};
        Random r = new Random();
        int ind = r.nextInt(winOrLose.length);
        return "Hello from Dobble! You " + winOrLose[ind];
    }

    /*
     * Send to winner he won and everyone else they lost
     */
    @MessageMapping("/amIWinner")
    public void amIWinner(AmIWinnerRequest request) {
        String clientID = request.getClientID();
        int shoutID = request.getShoutID();

        Optional<Player> player = this.playerService.getPlayer(clientID);

        if (!this.gameService.isWinner(clientID, shoutID)) {
            System.out.format("Client: %s didn't win shout %s", clientID, shoutID);
            return;
        }

        System.out.format("Client %s won shout %s", clientID, shoutID);

        Map<String, HumanPlayer> humanPlayers = this.playerService.getHumanPlayers();

        humanPlayers.remove(request.getClientID());
        humanPlayers.values().forEach(humanPlayer -> {
            Pair pair = this.gameService.getNextTurnState(humanPlayer.getClientId());
            AmIWinnerResponse loss = new AmIWinnerResponse(humanPlayer.getClientId(), shoutID, ResponseType.LOST, pair);
            this.communicationService.send(humanPlayer.getClientId(), "/queue/card-updates", loss);
        });

        Pair pair = this.gameService.getNextTurnState(clientID);
        if(player.isPresent()) {
            AmIWinnerResponse win = new AmIWinnerResponse(player.get().getClientId(), shoutID, ResponseType.WIN, pair);
            this.communicationService.send(player.get().getClientId(), "/queue/card-updates", win);
        }
    }

    @MessageMapping("/init")
    public void init(InitRequest request) {
        this.configurationService.setComputerPlayersNumber(request.getComputerPlayersNumber());
        this.configurationService.setComputerDifficulty(request.getComputerDifficulty());
        this.configurationService.startGameSession();

        Map<String, HumanPlayer> humanPlayers = this.playerService.getHumanPlayers();

        humanPlayers.values().forEach(humanPlayer -> {
            Pair pair = this.gameService.getNextTurnState(humanPlayer.getClientId());
            AmIWinnerResponse loss = new AmIWinnerResponse(humanPlayer.getClientId(), 1, ResponseType.NONE, pair);
            this.communicationService.send(humanPlayer.getClientId(), "/queue/card-updates", loss);
        });
    }

    @MessageMapping("/deletePlayer")
    public void deletePlayer(DeletePlayerRequest request) {
        this.adminPlayerService.deletePlayerFromGame(request.getPlayerToDeleteID());
    }

    @MessageMapping("/addPlayer")
    public void addPlayer(AddPlayerRequest request) {
        this.adminPlayerService.addPlayerToGame(request.getPlayerToAddName(), request.getPlayerToAddID());
    }

}
