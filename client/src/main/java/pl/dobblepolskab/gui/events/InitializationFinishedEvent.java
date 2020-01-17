package pl.dobblepolskab.gui.events;

import javafx.event.Event;
import javafx.event.EventType;
import websocket.ServerSDK;

public class InitializationFinishedEvent extends Event {
    public static final EventType<InitializationFinishedEvent> INITIALIZATION_FINISHED_EVENT_TYPE
            = new EventType<>("InitializationFinishedEvent");

    private ServerSDK connection;
    private int[] cards;
    private boolean isAdmin;

    public InitializationFinishedEvent(EventType<? extends Event> eventType, ServerSDK connection, int[] cards, boolean isAdmin) {
        super(eventType);

        this.connection = connection;
        this.cards = cards;
        this.isAdmin = isAdmin;
    }

    public ServerSDK getConnection() {
        return connection;
    }

    public int[] getCards() {
        return cards;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
