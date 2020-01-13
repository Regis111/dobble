package websocket;

import messages.responses.AmIWinnerResponse;
import messages.responses.InitResponse;

public interface NotifyService {
    void notifyAboutInit(InitResponse response);
    void notifyAboutNextTurn(AmIWinnerResponse response);
}
