package websocket;

import javafx.scene.Parent;
import messages.responses.AmIWinnerResponse;
import messages.responses.InitResponse;
import pl.dobblepolskab.gui.events.ServerRespondedEvent;

public class NotifyServiceImpl implements NotifyService {

    private final Parent gameObject;

    public NotifyServiceImpl(Parent gameObject) {
        this.gameObject = gameObject;
    }

    @Override
    public void notifyAboutInit(InitResponse response) {
        gameObject.fireEvent(new ServerRespondedEvent(ServerRespondedEvent.INITIALIZATION_MESSAGE_EVENT_TYPE, response));
    }

    @Override
    public void notifyAboutNextTurn(AmIWinnerResponse response) {
        gameObject.fireEvent(new ServerRespondedEvent(ServerRespondedEvent.NEXT_TURN_STARTED_EVENT_TYPE, response));
    }
}
