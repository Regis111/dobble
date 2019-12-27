package pl.dobblepolskab.controller;

import messages.Response;
import messages.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.HumanPlayer;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.Player;
import pl.dobblepolskab.services.CommunicationService;
import pl.dobblepolskab.services.GameService;
import pl.dobblepolskab.services.PlayerService;

import java.util.Map;
import java.util.Random;

@Controller
public class MainController {

    private final CommunicationService communicationService;

    private PlayerService playerService;

    private GameService gameService;

    @Autowired
    public MainController(CommunicationService communicationService,
                          PlayerService playerService,
                          GameService gameService) {
        this.communicationService = communicationService;
        this.playerService = playerService;
        this.gameService = gameService;
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
    public void amIWinner(Response request) {
        String clientID = request.getClientID();
        int shoutID = request.getShoutID();

        Player player = this.playerService.getPlayer(clientID);

        if (!this.gameService.isWinner(clientID, shoutID)) {
            System.out.format("Client: %s didn't win shout %s", clientID, shoutID);
            return;
        }

        System.out.format("Client %s won shout %s", clientID, shoutID);

        Map<String, HumanPlayer> humanPlayers = this.playerService.getHumanPlayers();

        humanPlayers.remove(request.getClientID());
        humanPlayers.values().forEach(humanPlayer -> {
            Response loss = new Response(humanPlayer.getClientId(), shoutID, ResponseType.LOST);
            this.communicationService.send(humanPlayer.getClientId(), "/queue/card-updates", loss);
        });

        Response win = new Response(player.getClientId(), shoutID, ResponseType.WIN);
        this.communicationService.send(player.getClientId(), "/queue/card-updates", win);
        // TODO new session establish
    }

    @MessageMapping("/init")
    public void init() {

    }

    @MessageMapping("/deletePlayer")
    public void deletePlayer() {

    }

    @MessageMapping("/addPlayer")
    public void addPlayer() {

    }

}
