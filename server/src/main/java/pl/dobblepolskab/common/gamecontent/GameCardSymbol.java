package pl.dobblepolskab.common.gamecontent;

import java.awt.image.BufferedImage;

public class GameCardSymbol {
    private int symbolId;
    private BufferedImage image;

    private void initClass(){
        symbolId = 0;
        image = null;
    }

    public GameCardSymbol(){
        initClass();
    }

    public GameCardSymbol(BufferedImage symbolImage){
        initClass();
        setImage(symbolImage);
    }

    public void setImage(BufferedImage symbolImage){
        image = symbolImage.getSubimage(0, 0, symbolImage.getWidth(), symbolImage.getHeight());
        symbolId = symbolImage.getData().hashCode();
    }

    public int getSymbolId(){
        return symbolId;
    }


}
