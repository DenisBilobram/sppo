package lab3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.JavascriptExecutor;

import java.util.HashMap;
import java.util.Map;

public class SearchAviaTicketsWithoutDirectionTest {

  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;

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
  public void searchAviaTicketsWithoutDirection() throws InterruptedException {
    driver.get("https://www.tbank.ru/travel/");
    Thread.sleep(1000);
    driver.findElement(By.xpath("//div[3]/div/div/div/div/div[2]")).click();
    Thread.sleep(1000);
    driver.findElement(By.xpath("//button[2]")).click();
    driver.findElement(By.xpath("//tr[4]/td[5]/span/div/div/div")).click();
    driver.findElement(By.xpath("//div[6]/div/div/div")).click();
    driver.findElement(By.xpath("//div[2]/label/span/span/input")).click();
    driver.findElement(By.xpath("//div[2]/div[2]/button/span/span")).click();

    WebElement dataFeild = driver.findElement(By.xpath("//div/div/div[7]/div/div/form/div[1]/div[1]/div/div/div[2]/div/div/div/div/div"));
    assertTrue(Boolean.valueOf(dataFeild.getAttribute("data-error")).booleanValue());
  }
}
