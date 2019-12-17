package pl.dobblepolskab.common.gamecontent;

import java.awt.image.BufferedImage;

public class GameCardSymbol {
    private int symbolId;
    private BufferedImage image;

    public GameCardSymbol(){
        initObject();
    }

    public GameCardSymbol(BufferedImage symbolImage){
        initObject();
        setImage(symbolImage);
    }

    private void initObject(){
        symbolId = 0;
        image = null;
    }

    public void setImage(BufferedImage symbolImage){
        image = symbolImage.getSubimage(0, 0, symbolImage.getWidth(), symbolImage.getHeight());
        symbolId = symbolImage.getData().hashCode();
    }

    public int getSymbolId(){
        return symbolId;
    }


}
