package pl.dobblepolskab.gui.others;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.NamedArg;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.util.Duration;
import pl.dobblepolskab.gui.events.TimerFinishedEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Timer extends Text {
    private static final int DISPLAY_OFFSET = 1;
    private static final int SECS_TO_MS = 1000;

    private DateFormat displayFormat;
    private int startingTime;
    private int time;

    public Timer(@NamedArg("format") String format) {
        this.displayFormat = new SimpleDateFormat(format);
    }

    public void run(int startingTime) {
        this.time = (startingTime + DISPLAY_OFFSET) * SECS_TO_MS;
        this.startingTime = time;

        setText(displayFormat.format(time));

        Timeline timeline = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.ONE, event -> {
            if (time <= 0) {
                timeline.stop();
                fireEvent(new TimerFinishedEvent(TimerFinishedEvent.TIMER_FINISHED_EVENT_TYPE));
            } else {
                time--;
                setText(displayFormat.format(time));
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void initialize() {

    }

    public void refresh() {
        time = startingTime;
    }
}
