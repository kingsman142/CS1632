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

    //When a user is on the front page and clicks the "Parse"
    //  button without entering any code into the code text area,
    //  they should be redirected to a new page with the basic
    //  templated for parsed code.
    //In this case, the user should see two <code> elements
    //  containing text relating to void_stmt, the default
    //  template that the compiler feeds to the user.
    @Test
    public void ClickingParseButtonWithNoCodeShouldReturnBaseCodeBlock() {
        driver.get("http://lit-bayou-7912.herokuapp.com/");

        //Find all "commit" buttons on the page
        List<WebElement> commitButtons = driver.findElements(By.name("commit"));
        WebElement parseButton = null;

        //Find the commit button with the value "Tokenize"
        for(WebElement element : commitButtons){
            String elementValue = element.getAttribute("value");
            if(elementValue.equals("Parse")){
                parseButton = element;
                break;
            }
        }

        //Make sure the button has been found
        assertNotNull(parseButton);

        parseButton.click();

        List<WebElement> codeBlocks = driver.findElements(By.tagName("code"));
        String firstCodeBlock = codeBlocks.get(0).getText();
        String secondCodeBlock = codeBlocks.get(1).getText();
        assertEquals("program [[:void_stmt]]", firstCodeBlock);
        assertEquals("program --void_stmt", secondCodeBlock);
    }

    //When a user enters the home page of the Ruby compiler
    //  website, the title of the website (the text that
    //  appears on the browser's tab) should be "Hoodpopper".
    @Test
    public void HoodpopperWebsiteShouldHaveTitleOfHoodpopper(){
        driver.get("http://lit-bayou-7912.herokuapp.com/");
        String pageTitle = driver.getTitle();
        assertEquals("Hoodpopper", pageTitle);
    }
}
