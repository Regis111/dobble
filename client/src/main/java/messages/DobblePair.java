package messages;

public class DobblePair {

    private int imageId;
    private int clientId;

    public int getImageId() {
        return imageId;
    }

    public int getClientId() {
        return clientId;
    }

    public DobblePair(int imageId, int clientId) {
        this.imageId = imageId;
        this.clientId = clientId;
    }
}
