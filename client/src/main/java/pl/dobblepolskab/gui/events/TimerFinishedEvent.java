package pl.dobblepolskab.gui.events;

import javafx.event.Event;
import javafx.event.EventType;

public class TimerFinishedEvent extends Event {
    public static final EventType<TimerFinishedEvent> TIMER_FINISHED_EVENT_TYPE = new EventType<>("TimerFinishedEvent");

    public TimerFinishedEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
