package lab3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class TBankTravelTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    @BeforeEach
    public void setUp() {
        try {

            System.setProperty("webdriver.chrome.driver", "C:\\Users\\denis\\Documents\\sppo\\3\\tpo\\lab3\\app\\src\\test\\resources\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            driver = new ChromeDriver(options);

            driver.manage().window().setSize(new Dimension(1200, 800));
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            js = (JavascriptExecutor) driver;

        } catch (Exception e) {
            e.printStackTrace();
            fail("Не удалось инициализировать ChromeDriver: " + e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (WebDriverException e) {
                System.err.println("Ошибка при driver.quit(): " + e.getMessage());
            }
        }
    }

    @Test
    public void searchFlightsAndToggleHotels() {
        driver.get("https://www.tbank.ru/travel/");

        // 1) Поле "Откуда" – ввести "Москва" и нажать ENTER
        WebElement fromInput = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//span[text()=\"Откуда\"]/ancestor::div[contains(@data-qa-file,'StatelessInput')]//input")
        ));
        fromInput.click();
        fromInput.sendKeys("Москва");
        fromInput.sendKeys(Keys.ENTER);

        // 2) Поле "Куда" – ввести "Санкт-Петербург" и нажать ENTER
        WebElement toInput = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//span[text()=\"Куда\"]/ancestor::div[contains(@data-qa-file,'StatelessInput')]//input")
        ));
        toInput.click();
        toInput.sendKeys("Санкт-Петербург");
        toInput.sendKeys(Keys.ENTER);

        // 3) Поле "Когда" – ввести "10062002520062025"
        // Сначала кликнем, чтобы активировать поле
        WebElement whenContainer = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//span[text()=\"Когда\"]/ancestor::div[contains(@data-qa-type,'DateTextInput_not-focused_no-error')]")
        ));
        whenContainer.click();
        // Найдём вложенный <input> и отправим в него текст
        WebElement whenInput = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//span[text()=\"Когда\"]/ancestor::div[contains(@data-qa-type,'DateTextInput_not-focused_no-error')]//input")
        ));
        whenInput.sendKeys("10062002520062025");
        whenInput.sendKeys(Keys.ENTER);

        // 4) Тумблер "Открыть отели в новой вкладке"
        WebElement hotelToggle = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//span[.//span[text()=\"Открыть отели в новой вкладке\"]]//input[@type='checkbox']")
        ));
        if (!hotelToggle.isSelected()) {
            hotelToggle.click();
        }

        // 5) Кнопка "Найти"
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[.//span[text()=\"Найти\"]]")
        ));
        searchButton.click();

        // При необходимости можно добавить дополнительные проверки результатов здесь
    }
}
