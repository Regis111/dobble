package pl.dobblepolskab.model.server.serverconfigurator;

public class FileBasedServerConfigurator extends ServerConfigurator{
    String filePath;

    FileBasedServerConfigurator(String configFilePath){
        super();
        filePath = configFilePath;

    }
}
