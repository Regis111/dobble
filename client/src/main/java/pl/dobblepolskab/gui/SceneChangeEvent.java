package pl.dobblepolskab.gui;


import javafx.event.Event;
import javafx.event.EventType;

public class SceneChangeEvent extends Event {
    public static final EventType<SceneChangeEvent> SCENE_CHANGE_EVENT_TYPE = new EventType<>(ANY);

    private String newSceneUrl;

    public SceneChangeEvent(EventType<? extends Event> eventType, String newSceneUrl) {
        super(eventType);

        this.newSceneUrl = newSceneUrl;
    }

    public String getNewSceneUrl() {
        return newSceneUrl;
    }
}
