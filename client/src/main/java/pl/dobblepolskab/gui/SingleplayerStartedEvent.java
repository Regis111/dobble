package pl.dobblepolskab.gui;

import javafx.event.Event;
import javafx.event.EventType;

public class SingleplayerStartedEvent extends SceneChangedEvent {
    public static final EventType<SingleplayerStartedEvent> SINGLEPLAYER_STARTED_EVENT_TYPE = new EventType<>("SingleplayerStartedEvent");

    private DifficultyLevel level;

    public SingleplayerStartedEvent(EventType<? extends Event> eventType, String newSceneUrl, DifficultyLevel level) {
        super(eventType, newSceneUrl);

        this.level = level;
    }

    public DifficultyLevel getDifficultyLevel() {
        return level;
    }
}
