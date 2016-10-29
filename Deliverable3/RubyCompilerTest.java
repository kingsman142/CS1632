import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
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

        //Find the commit button with the value "Parse"
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

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        List<WebElement> codeBlocks = driver.findElements(By.tagName("code"));
        String firstCodeBlock = codeBlocks.get(0).getText();
        //String secondCodeBlock = codeBlocks.get(1).getText();
        assertEquals("program\n[[:void_stmt]]", firstCodeBlock);
        //assertEquals("program --void_stmt", secondCodeBlock);
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

    //When a user clicks the "Back" link on the Tokenize,
    //  Parse, or Compile pages, the website should redirected
    //  the user to the homepage.
    @Test
    public void BackLinkTakesUserToHomepage(){
        //Navigate to the homepage and enter text into the code area
        driver.get("http://lit-bayou-7912.herokuapp.com/");
        String codeText = "a = 5";
        WebElement codeArea = driver.findElement(By.id("code_code"));
        codeArea.sendKeys(codeText);

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

        //This navigates to the tokenize page
        tokenizeButton.click();

        //Find the Back link and click it
        WebElement backLink = driver.findElement(By.linkText("Back"));
        backLink.click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        System.out.println("url: " + driver.getCurrentUrl());
        String textInCodeArea = driver.findElement(By.id("code_code")).getAttribute("value");

        assertEquals(codeText, textInCodeArea);
    }

    @Test
    public void SettingAVariableToAnIntegerWithoutSpacesShouldHaveThreeTokens(){
        //Navigate to the homepage and enter text into the code area
        driver.get("http://lit-bayou-7912.herokuapp.com/");
        String codeText = "a=5";
        WebElement codeArea = driver.findElement(By.id("code_code"));
        codeArea.sendKeys(codeText);

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

        //This navigates to the tokenize page
        tokenizeButton.click();

        WebElement codeBlock = driver.findElement(By.tagName("code"));
        String tokenizeOutput = codeBlock.getText();

        assertEquals("[[1, 0], :on_ident, \"a\"]\n[[1, 1], :on_op, \"=\"]\n[[1, 2], :on_int, \"5\"]", tokenizeOutput);
    }

    //When a user is on the homepage and enters an l-value, such
    //  as "var", and clicks the "Tokenize" button, the output
    //  should display one line stating "var" was an identifier,
    //  or "on_ident".
    @Test
    public void CodingRubyLValueShouldShowUpAsIdentifierAfterTokenization(){
        driver.get("http://lit-bayou-7912.herokuapp.com/");
        String codeText = "var";
        WebElement codeArea = driver.findElement(By.id("code_code"));
        codeArea.sendKeys(codeText);

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

        //This navigates to the tokenize page
        tokenizeButton.click();

        WebElement codeBlock = driver.findElement(By.tagName("code"));
        String tokenizeOutput = codeBlock.getText();

        assertEquals("[[1, 0], :on_ident, \"var\"]", tokenizeOutput);
    }

    //When a user is on the homepage and enters the assignment
    //  operator (=) and clicks the "Tokenize" button, the output
    //  should display one line stating "=" was an identifier,
    //  or "on_op".
    @Test
    public void CodingRubyAssignmentOperatorShouldShowUpAsOperatorAfterTokenization(){
        driver.get("http://lit-bayou-7912.herokuapp.com/");
        String codeText = "=";
        WebElement codeArea = driver.findElement(By.id("code_code"));
        codeArea.sendKeys(codeText);

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

        //This navigates to the tokenize page
        tokenizeButton.click();

        WebElement codeBlock = driver.findElement(By.tagName("code"));
        String tokenizeOutput = codeBlock.getText();

        assertEquals("[[1, 0], :on_op, \"=\"]", tokenizeOutput);
    }

    //When the user is on the homepage of the website,
    //  they should be able to edit the text area
    //  to input compilable code.
    /*@Test
    public void TextAreaOnHomepageShouldBeEditableForCodeInput(){
        driver.get("http://lit-bayou-7912.herokuapp.com/");
        WebElement codeArea = driver.findElement(By.id("code_code"));
        //assertEditable(codeArea);
    }*/
}
