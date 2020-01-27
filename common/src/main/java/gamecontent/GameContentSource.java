package gamecontent;

import java.util.Optional;

public interface GameContentSource {
    Optional<GameCardSymbol> getNextGameCardSymbol();

    Optional<GameCard> getNextGameCard();
}
