package gamecontent;

public enum DifficultyLevel {
    Easy(10),
    Medium(7),
    Hard(5),
    Expert(4);

    private final int gameTimeInSeconds;

    private DifficultyLevel(int gameTimeInSeconds) {
        this.gameTimeInSeconds = gameTimeInSeconds;
    }

    public int getGameTimeInSeconds() {
        return gameTimeInSeconds;
    }
}
