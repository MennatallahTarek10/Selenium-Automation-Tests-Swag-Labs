import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SauceDemoLoginTest {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        // Set the path to the WebDriver executable
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testSuccessfulLogin() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        WebElement productTitle = driver.findElement(By.className("title"));
        Assert.assertTrue(productTitle.isDisplayed(), "Login was not successful.");
    }

    @Test
    public void testInvalidUsername() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("invalid_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        WebElement errorMsg = driver.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertEquals(errorMsg.getText(), "Epic sadface: Username and password do not match any user in this service");
    }

    @Test
    public void testInvalidPassword() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("wrong_password");
        driver.findElement(By.id("login-button")).click();
        WebElement errorMsg = driver.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertEquals(errorMsg.getText(), "Epic sadface: Username and password do not match any user in this service");
    }

    @Test
    public void testBlankUsername() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        WebElement errorMsg = driver.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertEquals(errorMsg.getText(), "Epic sadface: Username is required");
    }

    @Test
    public void testBlankPassword() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("login-button")).click();
        WebElement errorMsg = driver.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertEquals(errorMsg.getText(), "Epic sadface: Password is required");
    }

    @Test
    public void testLockedUser() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        WebElement errorMsg = driver.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertEquals(errorMsg.getText(), "Epic sadface: Sorry, this user has been locked out.");
    }
    @Test
    public void testSpecialCharactersInUsername() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("@#%^&*()");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        WebElement errorMsg = driver.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertEquals(errorMsg.getText(), "Epic sadface: Username and password do not match any user in this service");
    }

    @Test
    public void testLongUsername() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("user_with_a_very_long_username_exceeding_the_limit");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        WebElement errorMsg = driver.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertEquals(errorMsg.getText(), "Epic sadface: Username and password do not match any user in this service");
    }

    @Test
    public void testSQLInjectionInUsername() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("' OR 1=1 --");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        WebElement errorMsg = driver.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertEquals(errorMsg.getText(), "Epic sadface: Username and password do not match any user in this service");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}


