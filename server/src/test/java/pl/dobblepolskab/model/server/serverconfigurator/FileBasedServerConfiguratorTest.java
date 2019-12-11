package pl.dobblepolskab.model.server.serverconfigurator;

import org.junit.Assert;
import org.junit.Test;
import pl.dobblepolskab.common.gamecontent.GameCardSymbol;
import pl.dobblepolskab.common.gamecontent.GameContentBox;

public class FileBasedServerConfiguratorTest {
    private static String errorString = "Reading configuration from config file doesn't work!";

    @Test
    public void readConfigurationValidTest(){
        FileBasedServerConfigurator configurator = new FileBasedServerConfigurator(
                "build/resources/test/testFileBasedServerConfigurator/config1.txt");
        Assert.assertTrue(errorString, configurator.isConfigurationCompleted());
        Assert.assertEquals(errorString, 1, configurator.getAiIntelligenceLevel());
        Assert.assertEquals(errorString, 8091, configurator.getServerPortId());
    }

    @Test
    public void readConfigurationCorrupted1Test(){
        FileBasedServerConfigurator configurator = new FileBasedServerConfigurator(
                "build/resources/test/testFileBasedServerConfigurator/config2.txt");
        Assert.assertFalse(errorString, configurator.isConfigurationCompleted());
        Assert.assertEquals(errorString, 0, configurator.getAiIntelligenceLevel());
        Assert.assertEquals(errorString, 8091, configurator.getServerPortId());
    }

    @Test
    public void readConfigurationCorrupted2Test(){
        FileBasedServerConfigurator configurator = new FileBasedServerConfigurator(
                "build/resources/test/testFileBasedServerConfigurator/config3.txt");
        Assert.assertFalse(errorString, configurator.isConfigurationCompleted());
        Assert.assertEquals(errorString, 1, configurator.getAiIntelligenceLevel());
        Assert.assertEquals(errorString, 0, configurator.getServerPortId());
    }

    @Test
    public void readConfigurationCorrupted3Test(){
        FileBasedServerConfigurator configurator = new FileBasedServerConfigurator(
                "build/resources/test/testFileBasedServerConfigurator/config4.txt");
        Assert.assertFalse(errorString, configurator.isConfigurationCompleted());
        Assert.assertEquals(errorString, 0, configurator.getAiIntelligenceLevel());
        Assert.assertEquals(errorString, 0, configurator.getServerPortId());
    }
}
