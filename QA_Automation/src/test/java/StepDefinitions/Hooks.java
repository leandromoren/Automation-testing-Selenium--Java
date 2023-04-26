package StepDefinitions;

import Functions.CreateDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.IOException;

public class Hooks
{
    public static WebDriver driver;

    public Logger log = LogManager.getLogger(Hooks.class);

    public Scenario scenario = null;

    /*
    * Este tag se utiliza  para marcar un metodo que debe ejecutarse antes de cada
    * prueba. Este metodo se utiliza generalmente para inicializar los objetos necesarios
    * para la prueba.
    *  */
    @Before
    public void initDriver(Scenario scenario) throws IOException
    {
        log.info("#######");
        log.info("[ Configuration ] - Inicializando la configuracion del controlador ");
        log.info("#######");
        driver = CreateDriver.initConfig();
        this.scenario = scenario;
        log.info("#######");
        log.info("[ Scenario ] - " + scenario.getName());
        log.info("#######");
    }

    /*
    * Este tag se utiliza para marcar un metodo que debe ejecutarse despues
    * de cada prueba. Este metodo se utiliza generalmente para limpiar cualquier
    * objeto o recurso utilizado en la prueba.
    *  */
    @After
    public void embedScreenshot(Scenario scenario) throws IOException
    {
        if(scenario.isFailed())
        {
            try
            {
                scenario.log("El escenario fallo.");
                scenario.log("La URL de la pagina actual es " + driver.getCurrentUrl());
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot,"image/png" ,scenario.getName());
            }
            catch (WebDriverException somePlatformsDontSupportScreenshots)
            {
                System.err.println(somePlatformsDontSupportScreenshots.getMessage());
            }
        }
        log.info("#######");
        log.info("[ Driver Status ] - Limpiar y cerrar la instance del controlador ");
        log.info("#######");
        driver.quit();
    }
}
