package definitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.assertj.core.api.Assertions.*;

import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import static support.Hooks.getDriver;

public class SearchProductDefs {
    private static WebDriver driver;

    private static final Map<String, By> productToIdMapping = Map.of("Lego", By.xpath("//div[@class = 'item-grid']/div[1]//a[@title='Lego building block']"),
            "shoes", By.xpath("//div[@class = 'item-grid']"));


    @Given("user goes to {string}")
    public void iGoTOUrl(String url) throws InterruptedException {
        System.out.println("comes here");
        getDriver().get(url);

        Thread.sleep(1000);

    }

    @Then("page title should be {string}")
    public void titleOfPageVerification(String pageTitle) {
        if (pageTitle.equals("Your store. Home page title")) {
            String actualTitle = getDriver().getTitle();
            assertThat(pageTitle.equals(actualTitle)).isTrue();
        }

        if (pageTitle.equals("Your store. Shopping Cart")) {
        }
        String actualTitle = getDriver().getTitle();
        assertThat(pageTitle.equals(actualTitle)).isTrue();
    }

    @When("user search for product {string}")
    public void searchProduct(String productName) {
        System.out.println("enters searchproduct method");
        WebElement searchInput = getDriver().findElement(By.id("small-searchterms"));
        searchInput.sendKeys(productName);


    }

    @And("user clicks on {string} button")
    public void userSearchProduct(String buttonToBeClicked) {
        if (buttonToBeClicked.equals("Search")) {
            WebElement searchButton = getDriver().findElement(By.xpath("//button[text()='Search']"));
            searchButton.submit();
        }
        if (buttonToBeClicked.equals("go to cart")) {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
            WebElement cartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Go to cart')]")));
            cartButton.click();
        }
        if (buttonToBeClicked.equals("Check out")) {
            WebElement checkOutButton = getDriver().findElement(By.id("checkout"));
            checkOutButton.click();
        }
        if (buttonToBeClicked.equals("Checkout as Guest")) {
            WebElement checkOutAsGuestButton = getDriver().findElement(By.xpath("//button[contains(text(),'Checkout as Guest')]"));
            checkOutAsGuestButton.click();
        }

    }


    @Then("searchResult Page includes {string}")
    public void searchResultIncludesProduct(String productName) {

        WebElement productToSelect = getDriver().findElement(By.xpath("//div[@class = 'picture']"));
        productToSelect.isDisplayed();

    }


    @Then("click on the product {string}")
    public void clickProduct(String productName) {

        System.out.println("Before clicking element");
        getDriver().findElement(productToIdMapping.get(productName)).click();
        System.out.println("After clicking element");

    }

    @And("user adds quantity of {int} for the product")
    public void selectQuantityForTheProduct(int num) {

        WebElement quantityOfProduct = getDriver().findElement(By.id("product_enteredQuantity_49"));
        quantityOfProduct.clear();
        quantityOfProduct.sendKeys(String.valueOf(num));

    }

    @When("User adds product {string} to cart")
    public void addToCartTheProduct(String productToAdd) {

        WebElement addToCart = getDriver().findElement(By.xpath("//div[@class = 'add-to-cart-panel']/button[@class = 'button-1 add-to-cart-button']"));
        addToCart.click();
        System.out.println("inside add to cart method");

    }


    @Then("{string} message appears on top of the page")
    public void verifyMessageIsDisplayed(String messageDisplayed) {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement successMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(),'added')]")));
        System.out.println(successMessage.getText());
        assertThat(messageDisplayed.equals(successMessage.getText())).isTrue();
        WebElement closeOption = getDriver().findElement(By.xpath("//span[@title = 'Close']"));
        closeOption.click();

    }

    @When("user hoovers over shopping cart option")
    public void hooverOverElement() {
        WebElement elementToHover = getDriver().findElement(By.xpath("//li[@id='topcartlink']"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(elementToHover).perform();


    }


    @Then("the shopping cart should contain {int} product")
    public void theShoppingCartShouldContainProduct(int quantity) {
        List<WebElement> cartItems = getDriver().findElements(By.cssSelector(".cart")); // Replace with the actual locator
        System.out.println(cartItems.size());
        assertThat(Objects.equals(quantity, cartItems.size())).isTrue();

    }

    @And("total price should be {string}")
    public void totalPriceShouldBe(String totalPrice) {

        WebElement totalCostElement = getDriver().findElement(By.xpath("//tbody/tr[4]//span[@class= 'value-summary']"));
        String price = totalCostElement.getText();
        String totalPriceWithoutDollar = totalPrice.replace("$" , "");
        String actualPriceWithoutDollar = price.replace("$" , "");
        double expectedPrice = Double.parseDouble(totalPriceWithoutDollar);
        double actualPrice = Double.parseDouble(actualPriceWithoutDollar);
        assertThat(expectedPrice).isEqualTo(actualPrice);


    }

    @When("user checks Terms of Service checkbox")
    public void userChecksCheckbox() {
        WebElement termsOfServiceCheckBox = getDriver().findElement(By.id("termsofservice"));
        termsOfServiceCheckBox.click();

    }

    @Then("user is redirected to {string}")
    public void userIsRedirectedToLoginPage(String pageName) {
        if(pageName.equals("Login Page")) {
            WebElement loginPage = getDriver().findElement(By.xpath("//h1[contains(text(),'Sign In')]"));
            String loginPageText = loginPage.getText();
            assertThat(loginPageText.contains("Sign In")).isTrue();
        }
        if(pageName.equals("Checkout page")) {
            WebElement checkOutPage = getDriver().findElement(By.xpath("//h1[contains(text(),'Checkout')]"));
            String loginPageText = checkOutPage.getText();
            assertThat(loginPageText.contains("Checkout")).isTrue();
        }


    }

    @When("user provides login credentials")
    public void userProvidesLoginCredentials() {
        //WebElement email = getDriver().findElement();
    }

    @Given("user is on Login page")
    public void userIsOnLoginPage() {

    }
}




