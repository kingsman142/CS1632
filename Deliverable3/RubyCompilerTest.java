import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class RubyCompilerTest {
    private WebDriver driver = new HtmlUnitDriver();

    @Test
    public void testRubyTests() throws Exception {
        //driver.get(baseUrl + "/");
        driver.findElement(By.name("commit")).click();
    }
}
