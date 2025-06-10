package lab3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchJDTicketsTest {

    private WebDriver driver;
    private Map<String, Object> vars;
    private JavascriptExecutor js;

    @BeforeEach
    public void setUp() {
        try {
            driver = new FirefoxDriver();
            js = (JavascriptExecutor) driver;
            vars = new HashMap<>();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Не удалось инициализировать FirefoxDriver: " + e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void searchJDTickets() throws InterruptedException {
        driver.get("https://www.tbank.ru/travel/");
        
        driver.findElement(By.xpath("//li[4]/a/div/div/img")).click();
        Thread.sleep(1000);
        WebElement frameElement = driver.findElement(By.xpath("(//iframe)[1]"));
        driver.switchTo().frame(frameElement);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div/div[2]/div/div[2]/div/div/div/div[1]/div/div/form/div[2]/div/div/label/input")));

        driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]/div/div/div/div[1]/div/div/form/div[2]/div/div/label/input")).click();
        driver.findElement(By.xpath("//span[2]")).click();
        driver.findElement(By.xpath("//div[2]/div/div/span")).click();
        driver.findElement(By.xpath("//div[5]/div[5]/div/span")).click();
        driver.findElement(By.xpath("//div[3]/button/div")).click();
        driver.findElement(By.xpath("//div[6]/button/div")).click();

        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div/div[2]/div/div[2]/div/div[2]/div[2]/div/div/div[3]/div/div/div[2]/div[2]/div[2]/div")));
        List<WebElement> elements = driver.findElements(By.xpath("/html/body/div/div[2]/div/div[2]/div/div[2]/div[2]/div/div/div[3]/div/div/div[2]/div[2]/div[2]/div"));
        assertTrue(elements.size() > 0);
  }

}
