package pl.dobblepolskab.gui.events;

import javafx.event.Event;
import javafx.event.EventType;
import messages.responses.Response;

public class ServerRespondedEvent extends Event {
    public static final EventType<ServerRespondedEvent> INITIALIZATION_MESSAGE_EVENT_TYPE = new EventType<>("InitializationMessageEvent");
    public static final EventType<ServerRespondedEvent> NEXT_TURN_STARTED_EVENT_TYPE = new EventType<>("NextTurnStartedEvent");

    private Response response;

    public ServerRespondedEvent(EventType<? extends Event> eventType, Response response) {
        super(eventType);

        this.response = response;
    }

    public Response getResponse() {
        return response;
    }
}
