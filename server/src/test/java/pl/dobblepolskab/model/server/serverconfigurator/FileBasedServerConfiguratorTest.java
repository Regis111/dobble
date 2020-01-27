package pl.dobblepolskab.model.server.serverconfigurator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import gamecontent.GameCardSymbol;
import gamecontent.GameContentBox;

public class FileBasedServerConfiguratorTest {
    private static String errorString = "Reading configuration from config file doesn't work!";

    @ParameterizedTest
    @CsvSource({"build/resources/test/testFileBasedServerConfigurator/config1.txt, true, 1, 8091",
            "build/resources/test/testFileBasedServerConfigurator/config2.txt, false, 0, 8091",
            "build/resources/test/testFileBasedServerConfigurator/config3.txt, false, 1, 0",
            "build/resources/test/testFileBasedServerConfigurator/config4.txt, false, 0, 0",})
    public void readConfigurationValidTest(String configFilePath, boolean isValid, int expectedAIIntelligenceLevel,
                                           int expectedServerPortId){
        // given
        FileBasedServerConfigurator configurator = new FileBasedServerConfigurator(configFilePath);

        // when
        boolean completed = configurator.isConfigurationCompleted();
        int aiIntelligenceLevel = configurator.getAiIntelligenceLevel();
        int serverPortId = configurator.getServerPortId();

        // then
        if(isValid)
            Assert.assertTrue(errorString, completed);
        else
            Assert.assertFalse(errorString, completed);
        Assert.assertEquals(errorString, expectedAIIntelligenceLevel, aiIntelligenceLevel);
        Assert.assertEquals(errorString, expectedServerPortId, serverPortId);
    }
}
