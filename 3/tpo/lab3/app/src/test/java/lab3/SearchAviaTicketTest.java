package lab3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

public class SearchAviaTicketTest {

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
    public void searchAviaTicketTest() throws InterruptedException {
        driver.get("https://www.tbank.ru/travel/");
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[1]/div/div/div[7]/div/div/form/div[1]/div[1]/div/div/div[1]/div[1]/div/div/div/div/div[1]/div[2]/div/div[2]/div/input")));
        }
        driver.findElement(By.xpath("//div[1]/div/div/div[7]/div/div/form/div[1]/div[1]/div/div/div[1]/div[1]/div/div/div/div/div[1]/div[2]/div/div[2]/div/input")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[1]/div/div/div[7]/div/div/form/div[1]/div[1]/div/div/div[1]/div[1]/div/div/div/div/div[1]/div[2]/div/div[2]/div/input")).sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        driver.findElement(By.xpath("//div[1]/div/div/div[7]/div/div/form/div[1]/div[1]/div/div/div[1]/div[1]/div/div/div/div/div[1]/div[2]/div/div[2]/div/input")).sendKeys("Москва");

        driver.findElement(By.xpath("//div[1]/div/div/div[7]/div/div/form/div[1]/div[1]/div/div/div[1]/div[1]/div/div/div/div/div[1]/div[2]/div/div[2]/div/input")).sendKeys(Keys.ENTER);
        driver.findElement(By.xpath("//div[1]/div/div/div[7]/div/div/form/div[1]/div[1]/div/div/div[2]/div/div/div/div/div/div[1]/div[2]/div/div[2]/div/input")).sendKeys("Санкт-Петербург");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[1]/div/div/div[7]/div/div/form/div[1]/div[1]/div/div/div[2]/div/div/div/div/div/div[1]/div[2]/div/div[2]/div/input")).sendKeys(Keys.ENTER);
        driver.findElement(By.xpath("//div[3]/div/div/div/div/div[2]")).click();
        driver.findElement(By.xpath("//button[2]")).click();
        driver.findElement(By.xpath("//tr[4]/td[5]/span/div/div/div")).click();
        driver.findElement(By.xpath("//div[6]/div/div/div")).click();
        driver.findElement(By.xpath("//div[2]/label/span/span/input")).click();
        driver.findElement(By.xpath("//div[2]/div[2]/button/span/span")).click();
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[4]/div/div[2]/div/div/div/div/div[2]/div/div/div/div/div/div/div")));
        List<WebElement> elements = driver.findElements(By.xpath("//div[4]/div/div[2]/div/div/div/div/div[2]/div/div/div/div/div/div/div"));
        assert(elements.size() > 0);

        elements = driver.findElements(By.xpath("//div/div/div[3]/div[3]/div[2]/div[4]/div/div[2]/div/div/div/div/div[1]/div/div[1]/div/div[2]/h2"));
        assert (elements.size() > 0);

        String[] locations = elements.getFirst().getText().split(" – ");
        assertEquals(locations[0], "Москва");
        assertEquals(locations[1], "Санкт-Петербург");

        elements = driver.findElements(By.xpath(
            "//div/div/div[3]/div[3]/div[2]/div[4]/div/div[2]/div/div/div/div/div[1]/div/div[1]/div/div[2]/p"));
        assert (elements.size() > 0);

        String date = elements.getFirst().getText();
        assertEquals(date, "20 июня, пятница");
  }

}
