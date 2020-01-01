package pl.dobblepolskab.gui;


import javafx.event.Event;
import javafx.event.EventType;

public class SceneChangedEvent extends Event {
    public static final EventType<SceneChangedEvent> SCENE_CHANGED_EVENT_TYPE = new EventType<>("SceneChangedEvent");

    private String newSceneUrl;

    public SceneChangedEvent(EventType<? extends Event> eventType, String newSceneUrl) {
        super(eventType);

        this.newSceneUrl = newSceneUrl;
    }

    public String getNewSceneUrl() {
        return newSceneUrl;
    }
}
