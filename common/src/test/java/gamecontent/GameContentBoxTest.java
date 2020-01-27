package gamecontent;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

public class GameContentBoxTest {
    private static String errorString = "Validation of game content box doesn't work!";

    @ParameterizedTest
    @CsvSource({"build/resources/test/testGameContentBox, true",
            "build/resources/test/testGameContentBoxCorrupted1, false",
            "build/resources/test/testGameContentBoxCorrupted2, false",
            "build/resources/test/testGameContentBoxCorrupted3, false",
            "build/resources/test/testGameContentBoxCorrupted4, false",})
    public void bindToDirectoryTest(String boxPath, boolean expectedBindReturnValue) {
        // given
        GameContentBox gameContentBox = new GameContentBox();

        // when
        boolean bindPassed = gameContentBox.bindToDirectory(boxPath);

        // then
        if(expectedBindReturnValue)
            Assert.assertTrue(errorString, bindPassed);
        else
            Assert.assertFalse(errorString, bindPassed);
    }
}
