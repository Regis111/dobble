package websocket;

import messages.responses.Response;

public interface NotifyService {
    void notifyGui(Response response);
}
