import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class TestListener implements TestWatcher {
    public void testFailed(ExtensionContext context, Throwable cause) {
        Allure.getLifecycle().addAttachment("screenshot", "image/png", "png"
                , ((TakesScreenshot) BaseSeleniumTest.driver).getScreenshotAs(OutputType.BYTES));

        BaseSeleniumTest.driver.close();
        BaseSeleniumTest.driver.quit();
    }
}
