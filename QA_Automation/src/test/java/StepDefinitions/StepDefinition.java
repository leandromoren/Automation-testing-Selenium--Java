package StepDefinitions;

import Functions.CreateDriver;
import Functions.SeleniumFunctions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.eo.Se;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Properties;

public class StepDefinition
{
    WebDriver driver;

    SeleniumFunctions functions = new SeleniumFunctions();

    private static Properties prop = new Properties();

    private static InputStream is = CreateDriver.class.getResourceAsStream("../test.properties");

    private static Logger log = LogManager.getLogger(StepDefinition.class);

    public StepDefinition()
    {
        driver  = Hooks.driver;
    }

    @Given("^El cliente se encuentra en la pantalla de logeo")
    public void elClienteSeEncuentraEnPantallaLogeo() throws IOException
    {
        prop.load(is);
        String url = prop.getProperty("MainAppUrlBase");
        log.info(("Navegar a: " + url));
        driver.get(url);
        functions.pageStatus();
    }

    @Given("^El cliente ingresa al sitio principal (.*)$")
    public void elClienteSeLogeaConClaveCorrecta(String url) throws IOException
    {
        log.info("Navegar a: " + url);
        driver.get(url);
        functions.windowsHandle("Principal");
    }

    @Then("^El cliente cierra la aplicacion$")
    public void elClienteCierraLaAplicacion() throws IOException
    {
        driver.quit();
    }

    @Then("^Cargo la informacion del DOM (.*)$")
    public void cargoLaInformacionDelDom(String file) throws Exception
    {
        SeleniumFunctions.FileName = file;
        SeleniumFunctions.readJson();
        log.info("Inicializa el archivo: " + file);
    }

    /*@And("^Hago un clic en el elemento (.*)$")
    public void hagoUnClicEnElElemento(String element) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        functions.waitForElementPresent(element);
        driver.findElement(SeleniumElement).click();
        log.info("clic en el elemento: " + element);
    }*/

    @And("^Configuro el elemento (.*) con el texto (.*)$")
    public void configuroElElementoConElTexto(String element, String text) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        functions.waitForElementPresent(element);
        driver.findElement(SeleniumElement).sendKeys(text);
    }

    @And("^Configuro el texto (.*) en el menu desplegable (.*)$")
    public void configuroElTextoEnElMenuDesplegable(String option, String element) throws Exception
    {
        functions.selectOptionDropdownByText(element, option);
    }

    @And("^Configuro el indice (.*) en el menu desplegable (.*)$")
    public void configuroElIndiceEnElMenuDesplegable(int option, String element) throws Exception
    {
        functions.selectOptionDropdownByIndex(element, option);
    }

    @And("^Marco la casilla de verificacion que tiene (.*)$")
    public void marcoLaCasillaDeVerificacionQueTieneMr(String element) throws Exception
    {
        functions.waitForElementPresent(element);
        functions.checkCheckbox(element);
    }

    @And("^Elimino texto del elemento (.*)$")
    public void eliminarTextoDelElemento(String element) throws Exception
    {
        functions.eliminarTexto(element);
    }

    @And("^Hago clic en el elemento (.*)$")
    public void hagoClicEnElElemento(String element) throws Exception
    {
        functions.waitForElementPresent(element);
        functions.clickJSElement(element);
    }

    @And("^Me desplazo hasta (top|end) de la pagina$")
    public void meDesplazoHastaDeLaPagina(String to) throws Exception
    {
        functions.scrollPage(to);
    }

    @And("^Me desplazo al elemento (.*)$")
    public void meDesplazoAlElemento(String element) throws Exception
    {
        functions.scrollToElement(element);
    }

    @And("^Abro una nueva ventana con URL (.*)$")
    public void abroUnaNuevaVentana(String url) throws Exception
    {
        functions.openNewTabWithURL(url);
        Thread.sleep(5000);
    }

    @And("^Cambio a una nueva ventana$")
    public void cambioAUnaNuevaVentana()
    {
        System.out.println((driver.getWindowHandles()));
        for(String winHandle : driver.getWindowHandles())
        {
            System.out.println(winHandle);
            log.info("Cambiar a nuevas ventanas");
            driver.switchTo().window(winHandle);
        }
    }

    @And("^Voy a la ventana (.*?)$")
    public void voyALaVentana(String windowsName)
    {
        functions.windowsHandle(windowsName);
    }

    @Then("^Yo (accept|dismiss) la alerta$")
    public void yoAcceptLaAlerta(String espera)
    {
        functions.AlertAcceptOrDismiss(espera);
    }

    @And("^Tomo captura de pantalla (.*?)$")
    public void tomoCapturaDePantallaAlFinalDelTest(String captura) throws IOException
    {
        try {
            functions.screenShot(captura);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Then("^Confirmo si el (.*) contiene el texto (.*)$")
    public void confirmoSiContieneElTexto(String element, String text) throws Exception
    {
        functions.checkPartialTextElementPresent(element,text);
    }

    @Then("^Confirmo si el (.*) es igual a (.*)$")
    public void confirmoSiEsigualA(String element, String texto) throws Exception
    {
        functions.checkTextElementEqualTo(element, texto);
    }

    @Then("^Confirmar si muestra el elemento (.*)$")
    public void confirmarSiMuestraElElemento(String element) throws Exception
    {
        boolean isDisplayed = functions.isElementDisplayed(element);
        Assert.assertTrue("El elemento esta presente: " + element, isDisplayed);
    }

    @And("^Tomo captura de pantalla: (.*)$")
    public void tomoCapturaDePantallaAllure(String name)
    {
        functions.attachScreenshot(name);
    }
}
