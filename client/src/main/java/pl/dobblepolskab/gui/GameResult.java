package pl.dobblepolskab.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class GameResult {
    private SimpleStringProperty date;
    private SimpleStringProperty name;
    private SimpleStringProperty level;
    private SimpleIntegerProperty score;

    GameResult(String date, String name, String level, int score) {
        this.date = new SimpleStringProperty(date);
        this.name = new SimpleStringProperty(name);
        this.level = new SimpleStringProperty(level);
        this.score = new SimpleIntegerProperty(score);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String newDate) {
        date.set(newDate);
    }

    public SimpleStringProperty getDateProperty() {
        return date;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String newName) {
        name.set(newName);
    }

    public SimpleStringProperty getNameProperty() {
        return name;
    }

    public String getLevel() {
        return level.get();
    }

    public void setLevel(String newLevel) {
        level.set(newLevel);
    }

    public SimpleStringProperty getLevelProperty() {
        return level;
    }

    public int getScore() {
        return score.get();
    }

    public void setScore(int newScore) {
        score.set(newScore);
    }

    public SimpleIntegerProperty getScoreProperty() {
        return score;
    }
}
