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

public class SearchHotelsTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;

  @BeforeEach
  public void setUp() {
    driver = new FirefoxDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void searchHotels() {
    driver.get("https://www.tbank.ru/travel/");
    driver.findElement(By.xpath("//li[2]/a/div/div/img")).click();
    driver.findElement(By.xpath("//div[2]/div/div/div/div/div/div/div/div/div/div/div[2]/div")).click();
    driver.findElement(By.xpath("//div/input")).click();
    driver.findElement(By.xpath("//div/input")).sendKeys("Москва");
    driver.findElement(By.xpath("//div[2]/span[2]")).click();

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    wait.until(ExpectedConditions.presenceOfElementLocated(
      By.xpath("//tr[4]/td[5]/span/span")));
    driver.findElement(By.xpath("//tr[4]/td[5]/span/span")).click();
    driver.findElement(By.xpath("//tr[4]/td[6]/span/span")).click();
    driver.switchTo().defaultContent();
    driver.findElement(By.xpath("/html/body/div[1]/div/div[4]/div/div[3]/div/div[2]/div/div/div/div[2]/button/span/span/span/span/span")).click();

    wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    wait.until(ExpectedConditions.presenceOfElementLocated(
      By.xpath("/html/body/div[1]/div/div[4]/div/div/div[2]/div[2]/div[2]/div[3]/div/div/div[2]")));
    
    List<WebElement> elements = driver.findElements(By.xpath("/html/body/div[1]/div/div[4]/div/div/div[2]/div[2]/div[2]/div[3]/div/div/div[2]"));

    assertTrue(elements.size() > 0);
  }
    
}
