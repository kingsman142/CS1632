import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import java.util.*;

public class RubyCompilerTest {
    private WebDriver driver = new HtmlUnitDriver();

    //When a user is on the front page and clicks the "Tokenize"
    //  button without entering any code into the code text area,
    //  they should be redirected to a new page with the tokenized
    //  code.
    //In this case, the user should see an empty code element.
    @Test
    public void ClickingTokenizeButtonWithNoCodeShouldReturnEmptyCodeBlock() {
        driver.get("http://lit-bayou-7912.herokuapp.com/");

        //Find all "commit" buttons on the page
        List<WebElement> commitButtons = driver.findElements(By.name("commit"));
        WebElement tokenizeButton = null;
        System.out.println("length: " + commitButtons.size());

        //Find the commit button with the value "Tokenize"
        for(WebElement element : commitButtons){
            String elementValue = element.getAttribute("value");
            if(elementValue.equals("Tokenize")){
                tokenizeButton = element;
                break;
            }
        }

        //Make sure the button has been found
        assertNotNull(tokenizeButton);

        tokenizeButton.click();

        WebElement codeBlock = driver.findElement(By.tagName("code"));
        String codeBlockText = codeBlock.getText();

        assertEquals("", codeBlockText);
    }
}
