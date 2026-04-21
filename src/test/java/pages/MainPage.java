package pages;

import core.BaseSeleniumPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class MainPage extends BaseSeleniumPage {

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
        closeCookieBanner();
        PageFactory.initElements(driver, this);
    }
    private void closeCookieBanner() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(), 'Accept')]")
            ));
            acceptButton.click();
        } catch (TimeoutException e) {
            // Баннера нет — ничего не делаем
        }
    }

    public MainPage fillFields(String nameValue, String passwordValue, String emailValue) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOf(nameField));
        wait.until(ExpectedConditions.elementToBeClickable(nameField));
        nameField.sendKeys(nameValue);

        wait.until(ExpectedConditions.visibilityOf(nameField));
        wait.until(ExpectedConditions.elementToBeClickable(nameField));
        passwordField.sendKeys(passwordValue);

        wait.until(ExpectedConditions.elementToBeClickable(milkCheckbox));
        milkCheckbox.click();
        wait.until(ExpectedConditions.elementToBeClickable(coffeeCheckbox));
        coffeeCheckbox.click();

        long headerHeight = header.getSize().getHeight();
        scrollToElement(yellowCheckbox, headerHeight);


        wait.until(ExpectedConditions.elementToBeClickable(yellowCheckbox));

        yellowCheckbox.click();

        wait.until(ExpectedConditions.elementToBeClickable(dropdown));
        dropdown.click();
        wait.until(ExpectedConditions.elementToBeClickable(dropdownValue));
        dropdownValue.click();

        wait.until(ExpectedConditions.visibilityOf(emailField));
        wait.until(ExpectedConditions.elementToBeClickable(emailField));
        emailField.sendKeys(emailValue);

        String longestName = "";
        String currentName;

        for (WebElement instrumentName: instruments) {
            currentName = instrumentName.getText().trim();
            if (currentName.length() > longestName.length())
                longestName = currentName;
        }
        wait.until(ExpectedConditions.visibilityOf(messageField));
        wait.until(ExpectedConditions.elementToBeClickable(messageField));
        messageField.sendKeys("Longest name of instrument - " + longestName
                + "\nCount of instruments - " + instruments.size());

        scrollToElement(submitButton, headerHeight);


        wait.until(ExpectedConditions.elementToBeClickable(submitButton));

        submitButton.click();

        return this;
    }

    private void scrollToElement(WebElement element, long headerHeight) {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, arguments[0].getBoundingClientRect().top " +
                        "+ window.pageYOffset - arguments[1] - 20);", element, headerHeight);
    }
}
