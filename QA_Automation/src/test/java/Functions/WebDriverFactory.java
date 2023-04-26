package Functions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WebDriverFactory
{
    private static Properties prop = new Properties();

    private static InputStream is = CreateDriver.class.getResourceAsStream("../test.properties");

    private static Logger log = LogManager.getLogger(WebDriverFactory.class);

    private static String resourceFolder;

    private static WebDriverFactory instance = null;

    private WebDriverFactory(){}

    public static WebDriverFactory getInstance()
    {
        if(instance == null)
        {
            instance = new WebDriverFactory();
        }
        return instance;
    }

    public static WebDriver createNewWebDriver(String browser, String os) throws IOException
    {
        WebDriver driver;

        prop.load(is);

        resourceFolder = prop.getProperty("resourceFolder");

        if("FIREFOX".equalsIgnoreCase(browser))
        {
            if("WINDOWS".equalsIgnoreCase(os))
            {
                System.setProperty("webdriver.gecko.driver", resourceFolder + os + "/geckodriver.exe");
            }
            else
            {
                System.setProperty("webdriver.gecko.driver", resourceFolder + os + "/geckodriver");
            }
            driver = new FirefoxDriver();
        }
        else if("CHROME".equalsIgnoreCase(browser))
        {
            if("WINDOWS".equalsIgnoreCase(os))
            {
                System.setProperty("webdriver.chrome.driver", resourceFolder + os + "/chromedriver.exe");
            }
            else
            {
                System.setProperty("webdriver.chrome.driver", resourceFolder + os + "/chromedriver");
            }
            driver = new ChromeDriver();
        }
        else if("INTERNET EXPLORER".equalsIgnoreCase(browser))
        {
          System.setProperty("webdriver.ie.driver", resourceFolder + os + "/IEDriverServer.exe");
          driver = new InternetExplorerDriver();
        }
        else
        {
            log.error("The Driver is not selectes properly, invalid name: " + browser + ", " + os);
            return null;
        }
        driver.manage().window().maximize();
        return driver;
    }
}
