import core.BaseSeleniumTest;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;
import pages.TestListener;

import java.time.Duration;

@ExtendWith(TestListener.class)
public class FormFieldsTest extends BaseSeleniumTest {
    @Test
    public void fieldsTest() {
        String name = "testName";
        String password = "testPassword";
        String email = "name@example.com";

        MainPage mainPage = new MainPage();
        mainPage.fillFields(name, password, email);

        WebDriverWait waitAlert = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert alert = waitAlert.until(ExpectedConditions.alertIsPresent());

        Allure.addAttachment("Expected result", "text/plain", "Message received!");
        Allure.addAttachment("Alert message", "text/plain", alert.getText());

        Assertions.assertEquals("Message received!", alert.getText());
        alert.accept();
    }
}

