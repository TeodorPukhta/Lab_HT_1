package SmokeTest;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;

import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;
import static org.testng.AssertJUnit.assertTrue;

public class SmokeTests {
    private WebDriver webDriver;
    public static final String HOME_PAGE_URL = "https://www.google.com/";
    public static final String SEARCH_KEYWORD = "Cat";
    public static final String SEARCH_BUTTON = "//div[@class='lJ9FBc']//input[@class='gNO89b']";
    public static final String LIST_SEARCH_RESULT_TYPE_BUTTONS = "//div[@class='hdtb-mitem']";
    public static final String LIST_SEARCH_RESULT_IMAGES = "//div[@id='islrg']//div[@data-ved]//a//div/child::*";
    public static final String EXPECTED_TAGNAME = "img";

    @BeforeTest
    public void profileSetUp() {
        chromedriver().setup();
    }

    @BeforeMethod
    public void testSetUp() {
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();

    }

    @Test(priority = 1)
    public void checkThatImageSearchPageContainsImages() {
        webDriver.get(HOME_PAGE_URL);
        webDriver.findElement(By.xpath("//input[@name='q']")).sendKeys(SEARCH_KEYWORD);
        WebDriverWait wait = new WebDriverWait(webDriver,5);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SEARCH_BUTTON)));
        webDriver.findElement(By.xpath(SEARCH_BUTTON)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LIST_SEARCH_RESULT_TYPE_BUTTONS)));
        webDriver.findElements(By.xpath(LIST_SEARCH_RESULT_TYPE_BUTTONS)).get(0).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LIST_SEARCH_RESULT_IMAGES)));
        assertTrue(webDriver.findElements(By.xpath(LIST_SEARCH_RESULT_IMAGES)).get(1).getTagName().contains(EXPECTED_TAGNAME));
        File scrFile = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("src/screenshots/screenshot.png"));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        webDriver.close();
    }
}
