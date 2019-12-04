package pl.dobblepolskab.gamecontent;

public interface GameContentSource {
    GameCardSymbol getNextGameCardSymbol();

    GameCard getNextGameCard();
}
