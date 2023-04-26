package Functions;


import StepDefinitions.Hooks;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CreateDriver
{
    private static Properties prop = new Properties();

    private static InputStream is = CreateDriver.class.getResourceAsStream("../test.properties");

    private static CreateDriver instance = null;

    private static Logger log = LogManager.getLogger(CreateDriver.class);

    private static String browser;

    private static String os;

    private static String logLevel;

    private CreateDriver() throws  IOException
    {
        CreateDriver.initConfig();
    }

    public static WebDriver initConfig() throws IOException
    {
        WebDriver driver;

        try
        {
            log.info("#######");
            log.info("[ POM Configuration ] - Lee la configuracion de propiedades basicas del: ../test.properties");
            prop.load(is);
            browser = prop.getProperty("browser");
            os = prop.getProperty("os");
            logLevel = prop.getProperty("logLevel");
        } catch (IOException e)
        {
            log.error("initConfig Error", e);
        }

        log.info("[ POM Configuration ] - os: " + os + " | Browser: " + browser + " |");
        log.info("[ POM Configuration ] - Logger Level: " + logLevel);
        log.info("#######");
        driver = WebDriverFactory.createNewWebDriver(browser, os);
        return driver;
    }
}
