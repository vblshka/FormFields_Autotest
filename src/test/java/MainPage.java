import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class MainPage extends BaseSeleniumPage{

    @FindBy(xpath = "//input[@id='name-input']")
    private WebElement nameField;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordField;

    @FindBy(css = "#drink2")
    private WebElement milkCheckbox;

    @FindBy(css = "#drink3")
    private WebElement coffeeCheckbox;

    @FindBy(css = "#color3")
    private WebElement yellowCheckbox;

    @FindBy(xpath = "//header[@id='header']")
    private WebElement header;

    @FindBy(id = "automation")
    private WebElement dropdown;

    @FindBy(xpath = "//select[@name='automation']//option[@value='yes']")
    private WebElement dropdownValue;

    @FindBy(xpath = "//input[@data-testid='email']")
    private WebElement emailField;

    @FindBy(xpath = "//form//ul//li")
    private List<WebElement> instruments;

    @FindBy(css = "[data-testid='message']")
    private WebElement messageField;

    @FindBy(css = "[id='submit-btn']")
    private WebElement submitButton;

    public MainPage() {
        driver.get("https://practice-automation.com/form-fields/");
        PageFactory.initElements(driver, this);
    }

    public MainPage fillFields(String nameValue, String passwordValue, String emailValue) {
        nameField.sendKeys(nameValue);
        passwordField.sendKeys(passwordValue);

        milkCheckbox.click();
        coffeeCheckbox.click();

        long headerHeight = header.getSize().getHeight();
        scrollToElement(yellowCheckbox, headerHeight);

        WebDriverWait waitYellowCheckbox = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitYellowCheckbox.until(ExpectedConditions.elementToBeClickable(yellowCheckbox));

        yellowCheckbox.click();

        dropdown.click();
        dropdownValue.click();

        emailField.sendKeys(emailValue);

        String longestName = "";
        String currentName;

        for (WebElement instrumentName: instruments) {
            currentName = instrumentName.getText().trim();
            if (currentName.length() > longestName.length())
                longestName = currentName;
        }

        messageField.sendKeys("Longest name of instrument - " + longestName
                + "\nCount of instruments - " + instruments.size());

        scrollToElement(submitButton, headerHeight);

        WebDriverWait waitSubmitButton = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitSubmitButton.until(ExpectedConditions.elementToBeClickable(submitButton));

        submitButton.click();

        return this;
    }

    private void scrollToElement(WebElement element, long headerHeight) {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, arguments[0].getBoundingClientRect().top " +
                        "+ window.pageYOffset - arguments[1] - 20);", element, headerHeight);
    }
}
