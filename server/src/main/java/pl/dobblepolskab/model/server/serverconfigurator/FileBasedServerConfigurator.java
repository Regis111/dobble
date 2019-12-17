package pl.dobblepolskab.model.server.serverconfigurator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileBasedServerConfigurator extends ServerConfigurator{
    private String filePath;

    public FileBasedServerConfigurator(String configFilePath){
        filePath = configFilePath;
        readConfiguration();
    }

    private void readConfiguration(){
        File file = new File(filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String fileLine;
            while ((fileLine = reader.readLine()) != null) {
                if (fileLine.matches("AIIntelligenceLevel: (.*)")) {
                    String[] def = fileLine.split("AIIntelligenceLevel: ");
                    int level = Integer.parseInt(def[1]);
                    if(level != 1)
                        continue;
                    setAIIntelligenceLevel(level);
                }
                if (fileLine.matches("ServerPortId: (.*)")) {
                    String[] def = fileLine.split("ServerPortId: ");
                    int portId = Integer.parseInt(def[1]);
                    setServerPortId(portId);
                }
            }
        } catch (IOException e) {
            System.err.println("File " + filePath + " not found, so cannot configure Dobble Server Application.");
        }
        if(getAiIntelligenceLevel() == 0 || getServerPortId() == 0)
            return;
        completed = true;
    }
}
