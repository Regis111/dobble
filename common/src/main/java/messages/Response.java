package messages;

public class Response extends Message{
    private ResponseType type;

    public Response() {

    }

    public ResponseType getType() {
        return type;
    }

    public Response(String clientID, int shoutID, ResponseType type) {
        super(clientID, shoutID);
        this.type = type;
    }

    @Override
    public String toString() {
        return "Response{" +
                "type=" + type +
                ", clientID='" + clientID + '\'' +
                ", shoutID=" + shoutID +
                '}';
    }
}
