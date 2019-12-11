package pl.dobblepolskab.common.gamecontent;

public class GameCard {
    private long cardId;
    private int[] values;

    private void initClass() {
        cardId = 0;
        values = new int[8];
    }

    public GameCard() {
        initClass();
    }

    public GameCard(long cardId) {
        initClass();
        setCardId(cardId);
    }

    public GameCard(int[] valuesArray) {
        initClass();
        setValues(valuesArray);
    }

    public void setCardId(long cardId) {
        this.cardId = 0;
        for (int i = 0; i < 8; i++) {
            values[7 - i] = (int)(cardId % 50) + 1;
            cardId /= 50;
        }
        for (int i = 0; i < 8; i++)
            if (values[i] > 50 || values[i] < 1)
                return;
        this.cardId = cardId;
    }

    public void setValues(int[] valuesArray) {
        if (valuesArray.length < 8)
            return;
        for (int i = 0; i < 8; i++)
            if (valuesArray[i] > 50 || valuesArray[i] < 1)
                return;
        for (int i = 0; i < 8; i++) {
            values[i] = valuesArray[i];
            cardId *= 50;
            cardId += values[i] - 1;
        }
    }

    public long getCardId() {
        return cardId;
    }


}
