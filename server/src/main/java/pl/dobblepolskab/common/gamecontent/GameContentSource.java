package pl.dobblepolskab.common.gamecontent;

public interface GameContentSource {
    GameCardSymbol getNextGameCardSymbol();

    GameCard getNextGameCard();
}
