package gamecontent;

import java.security.InvalidParameterException;

public class GameCard {
    private long cardId;
    private int[] values;
    public static final int finalSymbolsNumber = 8;

    public GameCard() {
        initObject();
    }

    public GameCard(long cardId) {
        initObject();
        setCardId(cardId);
    }

    public GameCard(int[] valuesArray) {
        initObject();
        setValues(valuesArray);
    }

    private void initObject() {
        cardId = 0;
        values = new int[finalSymbolsNumber];
    }

    public void setCardId(long cardId) {
        this.cardId = 0;
        for (int i = 0; i < finalSymbolsNumber; i++) {
            values[finalSymbolsNumber - 1 - i] = (int)(cardId % GameContent.finalGameCardSymbolsNumber) + 1;
            cardId /= GameContent.finalGameCardSymbolsNumber;
        }
        for (int i = 0; i < finalSymbolsNumber; i++) {
            if (values[i] > GameContent.finalGameCardSymbolsNumber || values[i] < 1) {
                throw new InvalidParameterException("Cannot create right game card from this id!");
            }
        }
        this.cardId = cardId;
    }

    public void setValues(int[] valuesArray) {
        if (valuesArray.length < finalSymbolsNumber) {
            throw new InvalidParameterException("Cannot create right game card from this array (min size = 8)!");
        }
        for (int i = 0; i < finalSymbolsNumber; i++) {
            if (valuesArray[i] > GameContent.finalGameCardSymbolsNumber || valuesArray[i] < 1) {
                throw new InvalidParameterException("Cannot create right game card from this array (wrong data)!");
            }
        }
        for (int i = 0; i < finalSymbolsNumber; i++) {
            values[i] = valuesArray[i];
            cardId *= GameContent.finalGameCardSymbolsNumber;
            cardId += values[i] - 1;
        }
    }

    public int[] getValues() {
        return values;
    }

    public long getCardId() {
        return cardId;
    }


}
