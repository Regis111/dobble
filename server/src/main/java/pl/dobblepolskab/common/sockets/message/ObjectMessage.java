package pl.dobblepolskab.common.sockets.message;

public class ObjectMessage extends Message{
    private Object data;

    public ObjectMessage(String sender, Object data){
        super(sender);
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
