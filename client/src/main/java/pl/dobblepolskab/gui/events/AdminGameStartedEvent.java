package pl.dobblepolskab.gui.events;

import gamecontent.DifficultyLevel;
import javafx.event.Event;
import javafx.event.EventType;

public class AdminGameStartedEvent extends GameStartedEvent {
    public static final EventType<AdminGameStartedEvent> ADMIN_GAME_STARTED_EVENT_TYPE = new EventType<>("AdminGameStartedEvent");

    private int botsCount;

    public AdminGameStartedEvent(EventType<? extends Event> eventType, DifficultyLevel level, int botsCount) {
        super(eventType, level);

        this.botsCount = botsCount;
    }

    public int getBotsCount() {
        return botsCount;
    }
}
