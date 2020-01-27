package pl.dobblepolskab.gui.events;

import javafx.event.Event;
import javafx.event.EventType;

public class MultiplayerEndedEvent extends Event {
    public static final EventType<MultiplayerEndedEvent> MULTIPLAYER_ENDED_EVENT_TYPE = new EventType<>("MultiplayerEndedEvent");

    public MultiplayerEndedEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
