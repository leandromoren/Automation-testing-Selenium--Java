package Functions;

import StepDefinitions.Hooks;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils; //Importante que sea de commons, sino no funciona la ss
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.NoSuchElementException;

public class SeleniumFunctions
{
    private static Properties prop = new Properties();

    private static InputStream is = SeleniumFunctions.class.getResourceAsStream("../test.properties");

    public static final int EXPLICIT_TIMEOUT = 5;

    public String ElementText = "";

    public static boolean isDisplayed = Boolean.parseBoolean(null);

    public static Map<String, String> HandleMyWindows = new HashMap<>();

    public static String FileName = "";

    public static WebDriver driver;

    public static String PagesFilePath = "src/test/resources/Pages/";

    private static Logger log = LogManager.getLogger(SeleniumFunctions.class);

    public static String GetFieldBy = "";

    public static String ValueToFind = "";

    public String readProperties(String property) throws IOException
    {
        prop.load(is);
        return prop.getProperty(property);
    }
    public SeleniumFunctions()
    {
        driver = Hooks.driver;
    }

    /**Esta funcion lee cualquier archivo JSON*/
    public static Object readJson() throws Exception
    {
        FileReader reader = new FileReader(PagesFilePath + FileName);
        try
        {
            if(reader != null)
            {
                JSONParser jsonParser = new JSONParser();
                return jsonParser.parse(reader);
            }
            else
            {
                return null;
            }
        }
        catch (FileNotFoundException | NullPointerException e)
        {
            log.error("No existe el archivo " + FileName);
            throw new IllegalStateException("No existe el archivo " + FileName, e);
        }
    }

    public static JSONObject ReadEntity(String element) throws Exception
    {
        JSONObject Entity = null;
        JSONObject jsonObject = (JSONObject) readJson();
        Entity = (JSONObject) jsonObject.get(element);
        log.info(Entity.toJSONString());
        return Entity;
    }

    /**Esta funcion valida el tipo de identificador del elemento*/
    public static By getCompleteElement(String element) throws Exception
    {
        By result = null;
        JSONObject Entity = ReadEntity(element);
        GetFieldBy = (String) Entity.get("GetFieldBy");
        ValueToFind = (String) Entity.get("ValueToFind");
        if("classname".equalsIgnoreCase(GetFieldBy))
        {
            result = By.className(ValueToFind);
        }
        else if("cssSelector".equalsIgnoreCase(GetFieldBy))
        {
            result = By.cssSelector(ValueToFind);
        }
        else if("id".equalsIgnoreCase(GetFieldBy))
        {
            result = By.id(ValueToFind);
        }
        else if("linkText".equalsIgnoreCase(GetFieldBy))
        {
            result = By.linkText(ValueToFind);
        }
        else if("name".equalsIgnoreCase(GetFieldBy))
        {
            result = By.name(ValueToFind);
        }
        else if("link".equalsIgnoreCase(GetFieldBy))
        {
            result = By.partialLinkText(ValueToFind);
        }
        else if("tagName".equalsIgnoreCase(GetFieldBy))
        {
            result = By.tagName(ValueToFind);
        }
        else if("xpath".equalsIgnoreCase(GetFieldBy))
        {
            result = By.xpath(ValueToFind);
        }
        return result;
    }

    public void selectOptionDropdownByText(String element, String option) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        log.info(String.format("Elemento de espera: %s", element));
        Select opt = new Select(driver.findElement(SeleniumElement));
        log.info("Seleccionar opcion: " + option + " by text");
        opt.selectByVisibleText(option);
    }

    public void selectOptionDropdownByIndex(String element, int option) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        log.info(String.format("Elemento de espera: %s", element));
        Select opt = new Select(driver.findElement(SeleniumElement));
        log.info("Seleccionar opcion: " + option + " by index");
        opt.selectByIndex(option);
    }

    public void selectOptionDropdownByValue(String element, String option) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        log.info(String.format("Elemento de espera: %s", element));
        Select opt = new Select(driver.findElement(SeleniumElement));
        log.info("Seleccionar opcion: " + option + " by value");
        opt.selectByValue(option);
    }

    /**Esta funcion espera que el elemento este presente en el DOM*/
    public void waitForElementPresent(String element) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_TIMEOUT));
        log.info("Esperando el elemento: " + element + " que este presente");
        w.until(ExpectedConditions.presenceOfElementLocated(SeleniumElement));
    }

    /**Esta funcion espera que el elemento sea visible*/
    public void waitForElementVisible(String element) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_TIMEOUT));
        log.info("Esperando el elemento: " + element + " que este visible");
        w.until(ExpectedConditions.visibilityOfElementLocated(SeleniumElement));
    }

    public void checkCheckbox(String element) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        boolean isChecked = driver.findElement(SeleniumElement).isSelected();
        //Si el checkbox no esta seleccionado, hace clic para seleccionarlo
        if(!isChecked)
        {
            log.info("Haciendo clic en la casilla de verificacion para seleccionar: " + element);
            driver.findElement(SeleniumElement).click();
        }
    }

    public void uncheckCheckbox(String element) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        boolean isChecked = driver.findElement(SeleniumElement).isSelected();
        //Si el checkbox esta seleccionado, hace clic para des-seleccionarlo
        if(isChecked)
        {
            log.info("Haciendo clic en la casilla de verificacion para seleccionar: " + element);
            driver.findElement(SeleniumElement).click();
        }
    }

    public void eliminarTexto(String element) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        driver.findElement(SeleniumElement).clear();
    }

    /**Esta funcion asegura clickear un elemento sin la necesidad de
     * hacer un hover para que ese elemento se visualice si fuera el caso
     * por lo tanto es lo mas seguro y recomendable utilizar
     * JavascriptExecutor*/
    public void clickJSElement(String element) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        /**Le anexo la variable al driver*/
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        log.info("Click al elemento: " + element);
        jse.executeScript("arguments[0].click()", driver.findElement(SeleniumElement));
    }

    /**Esta funcion me permite scrollear de punta a punta la pagina */
    public void scrollPage(String to) throws Exception
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        if(to.equals("top"))
        {
            log.info("Desplazarse hasta la parte superior de la pagina");
            js.executeScript("scroll(0,-250);");
        }
        else if (to.equals("end"))
        {
            log.info("Desplazarse hasta el final de la pagina");
            js.executeScript("scroll(0,250);");
        }
    }

    /**Esta funcion me permite scrollear hasta un determinado
     * elemento de la pagina */
    public void scrollToElement(String element) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        log.info("Desplazarse al elemento: " + element);
        js.executeScript("arguments[0].scrollIntoView();",driver.findElement(SeleniumElement));
    }

    public void pageStatus()
    {
        try
        {
            String GetActual = driver.getCurrentUrl();
            System.out.println(String.format("Comprobando si la pagina esta cargada.", GetActual));
            log.info(String.format("Comprobando si la pagina esta cargada.", GetActual));
            new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_TIMEOUT)).until(
                    webDriver -> ((JavascriptExecutor) webDriver)
                            .executeScript("return document.readyState")
                            .equals("Complete.")
            );
        } catch (Exception e)
        {
            log.error("Error en el estatus de la pagina: " + e.getMessage());
        }
    }

    /**Esta funcion me permite abrir una o varias ventanas nuevas */
    public void openNewTabWithURL(String URL)
    {
        try
        {
            log.info("Abrir una ventana nueva con URL: " + URL);
            System.out.println("Abrir ventana nueva con URL: " + URL);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(String.format("window.open('%s','_blank');", URL));
        }
        catch (Exception e)
        {
            log.error("Error al abrir nueva ventanta: " + e.getMessage());
        }
    }

    /**Con esta funcion valido si ya existe la ventana */
    public void windowsHandle(String windowsName)
    {
        /**Si la ventana ya existe haz un switch y si no existe
         * guardala dentro de la key y le pongo el nombre que le
         * voy a pasar y guarde su valor hexadecimal para luego ejecutarlo*/
        if(this.HandleMyWindows.containsKey(windowsName))
        {
            driver.switchTo().window(this.HandleMyWindows.get(windowsName));
            log.info(String.format("Voy a Windows: %s con valor: %s ", windowsName, this.HandleMyWindows.get(windowsName)));
        }
        else
        {
            for(String winHandle : driver.getWindowHandles())
            {
                this.HandleMyWindows.put(windowsName, winHandle);
                System.out.println("La nueva ventana " + windowsName + "se guarda en escenario con valor " +
                        this.HandleMyWindows.get(windowsName));
                log.info("La nueva ventana " + windowsName + "se guarda en escenario con valor " +
                        this.HandleMyWindows.get(windowsName));
                driver.switchTo().window(winHandle);
            }
        }
    }

    /**Esta funcion valida si las alertas son aceptadas o rechazadas */
    public void AlertAcceptOrDismiss(String espera)
    {
        try
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_TIMEOUT));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            System.out.println(alert.getText());
            if(espera.equals("accept"))
            {
                alert.accept();
                log.info("La alerta fue aceptada con exito.");
                System.out.println("La alerta fue aceptada con exito");
            }
            else
            {
                alert.dismiss();
                log.info("La alerta se rechazo con exito");
                System.out.println("La alerta se rechazo con exito");
            }
        } catch (Throwable e)
        {
            log.error("Se produjo un error mientras esperaba la" +
                    " ventana emergente de alerta." + e.getMessage());
        }
    }

    public void screenShot(String testCapture) throws IOException
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String screenShotName = readProperties("ScreenShotPath") + "\\" +
                readProperties("browser") + "\\" + testCapture + "_(" +
                dateFormat
                        .format(GregorianCalendar.getInstance().getTime()) + ")";
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, new File(String.format("%s.png", screenShotName)));
    }

    public String getTextElement(String element) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_TIMEOUT));
        wait.until(ExpectedConditions.presenceOfElementLocated(SeleniumElement));
        ElementText = driver.findElement(SeleniumElement).getText();
        return ElementText;
    }

    public void checkPartialTextElementNotPresent(String element, String text) throws Exception
    {
        ElementText = getTextElement(element);
        boolean isFoundFalse  = ElementText.indexOf(text) != -1 ? true : false;
        Assert.assertFalse("El texto esta presente en el elemento: " + element + " el texto actual es: " + ElementText, isFoundFalse);
    }

    public void checkPartialTextElementPresent(String element, String text) throws Exception
    {
        ElementText = getTextElement(element);
        boolean isFound  = ElementText.indexOf(text) != -1 ? true : false;
        Assert.assertTrue("El texto no esta presente en el elemento: " + element + " el texto actual es: " + ElementText, isFound);
    }

    public void checkTextElementEqualTo(String element, String text) throws Exception
    {
        ElementText = getTextElement(element);
        Assert.assertEquals("El texto esta presente en el elemento: " + element + " el texto actual es: " + ElementText, text, ElementText);
    }

    public boolean isElementDisplayed(String element) throws Exception
    {
        try
        {
            By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_TIMEOUT));
            isDisplayed = wait.until(ExpectedConditions.presenceOfElementLocated(SeleniumElement)).isDisplayed();
        }
        catch (NoSuchElementException | TimeoutException e)
        {
            isDisplayed = false;
            log.info(e);
        }
        return isDisplayed;
    }

    public byte[] attachScreenshot(String nameCapture)
    {
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment(nameCapture, new ByteArrayInputStream(screenshot));
        return screenshot;
    }

}
