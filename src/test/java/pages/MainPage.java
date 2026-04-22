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
            // Пробуем разные варианты кнопок
            By[] acceptButtons = {
                    By.xpath("//button[contains(text(), 'Accept')]"),
                    By.xpath("//button[contains(text(), 'OK')]"),
                    By.xpath("//button[contains(text(), 'Agree')]"),
                    By.xpath("//button[contains(text(), 'Got it')]"),
                    By.cssSelector(".cookie-accept"),
                    By.cssSelector(".cookie-consent button")
            };

            for (By locator : acceptButtons) {
                try {
                    WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    acceptButton.click();
                    System.out.println("Cookie banner closed");
                    break;
                } catch (TimeoutException ignored) {
                    // Пробуем следующий селектор
                }
            }
        } catch (Exception e) {
            // Баннера нет — ничего не делаем
            System.out.println("No cookie banner found");
        }
    }

    public MainPage fillFields(String nameValue, String passwordValue, String emailValue) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

//        wait.until(ExpectedConditions.visibilityOf(nameField));
//        wait.until(ExpectedConditions.elementToBeClickable(nameField));
        logElementState(nameField, "nameField");
        nameField.sendKeys(nameValue);

        logElementState(passwordField, "passwordField");
        wait.until(ExpectedConditions.visibilityOf(nameField));
        wait.until(ExpectedConditions.elementToBeClickable(nameField));
        passwordField.sendKeys(passwordValue);

        logElementState(milkCheckbox, "milkcheckbox");
        wait.until(ExpectedConditions.elementToBeClickable(milkCheckbox));
        safeClick(milkCheckbox);

        logElementState(coffeeCheckbox, "coffeecheckbox");
        wait.until(ExpectedConditions.elementToBeClickable(coffeeCheckbox));
        safeClick(coffeeCheckbox);

        long headerHeight = header.getSize().getHeight();
        scrollToElement(yellowCheckbox, headerHeight);

        logElementState(yellowCheckbox, "yellowcheckbox");
        wait.until(ExpectedConditions.elementToBeClickable(yellowCheckbox));
        safeClick(yellowCheckbox);

        logElementState(dropdown, "dropdown");
        wait.until(ExpectedConditions.elementToBeClickable(dropdown));
        safeClick(dropdown);

        logElementState(dropdownValue, "dropdownvalue");
        wait.until(ExpectedConditions.elementToBeClickable(dropdownValue));
        safeClick(dropdownValue);

        logElementState(emailField, "emailfield");
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

        logElementState(messageField, "messagefield");
        wait.until(ExpectedConditions.visibilityOf(messageField));
        wait.until(ExpectedConditions.elementToBeClickable(messageField));
        messageField.sendKeys("Longest name of instrument - " + longestName
                + "\nCount of instruments - " + instruments.size());

        scrollToElement(submitButton, headerHeight);

        logElementState(submitButton, "submit");
        wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        safeClick(submitButton);

        return this;
    }

    private void scrollToElement(WebElement element, long headerHeight) {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, arguments[0].getBoundingClientRect().top " +
                        "+ window.pageYOffset - arguments[1] - 20);", element, headerHeight);
    }

    public void logElementState(WebElement element, String name) {
        System.out.println("--- " + name + " ---");
        System.out.println("isDisplayed: " + element.isDisplayed());
        System.out.println("isEnabled: " + element.isEnabled());
        System.out.println("Location: " + element.getLocation());
        System.out.println("Size: " + element.getSize());
    }
}
