package gamecontent;

public class GameCardSymbol {
    private int symbolId;
    private String imagePath;

    public GameCardSymbol(){
        initObject();
    }

    public GameCardSymbol(String imagePath){
        initObject();
        setImagePath(imagePath);
    }

    private void initObject(){
        symbolId = 0;
        imagePath = "";
    }

    public void setImagePath(String imagePath){
        this.imagePath = imagePath;
        symbolId = imagePath.hashCode();
    }

    public int getSymbolId(){
        return symbolId;
    }

    public String getImagePath() {
        return imagePath;
    }
}
