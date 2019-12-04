package pl.dobblepolskab.gamecontent;

import java.io.File;

public class GameContentBox implements GameContentSource{
    private String directoryPath;

    private void initClass(){
        directoryPath = "";
    }

    public GameContentBox(){
        initClass();
    }

    public GameContentBox(String directoryPath){
        initClass();
        bindToDirectory(directoryPath);
    }

    public boolean bindToDirectory(String directoryPath){
        File boxDescFile = new File(directoryPath + File.separator + "gameContentBoxDef.txt");
        return true;
    }

    @Override
    public GameCardSymbol getNextGameCardSymbol() {
        return null;
    }

    @Override
    public GameCard getNextGameCard() {
        return null;
    }
}
