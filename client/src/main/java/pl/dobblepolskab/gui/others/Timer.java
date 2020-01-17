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
    private static final long DISPLAY_OFFSET = 1000;

    private DateFormat displayFormat;
    private long startingTime;
    private long time;

    public Timer(@NamedArg("format") String format) {
        this.displayFormat = new SimpleDateFormat(format);
    }

    public void run(long startingTime) {
        this.startingTime = startingTime + DISPLAY_OFFSET;
        this.time = startingTime + DISPLAY_OFFSET;
        setText(displayFormat.format(time));

        Timeline timeline = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.ONE, event -> {
            if (time <= 0) {
                timeline.stop();
                fireEvent(new TimerFinishedEvent(TimerFinishedEvent.TIMER_FINISHED_EVENT_TYPE));
            }
            time--;
            setText(displayFormat.format(time));
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
