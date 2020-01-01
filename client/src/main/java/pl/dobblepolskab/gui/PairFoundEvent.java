package pl.dobblepolskab.gui;

import javafx.event.Event;
import javafx.event.EventType;

public class PairFoundEvent extends Event {
    public static final EventType<PairFoundEvent> PAIR_FOUND_EVENT_TYPE = new EventType<>("PairFoundEvent");

    public PairFoundEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
