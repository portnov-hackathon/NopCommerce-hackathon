package definitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.assertj.core.api.Assertions.*;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import static support.Hooks.getDriver;

public class ProductSearchStepDefs {
    private static WebDriver driver;
    WebElement size;
    private static final Map<String, By> productToIdMapping = Map.of("Lego", By.xpath("//div[@class = 'item-grid']/div[1]//a[@title='Lego building block']"),
            "Nike Floral Roshe Customized Running Shoes", By.xpath("//h2[@class= 'product-title']//a[text() = 'Nike Floral Roshe Customized Running Shoes']"));

    @Given("user goes to {string}")
    public void iGoTOUrl(String url) throws InterruptedException {
        System.out.println("Lands on HomePage");
        getDriver().get(url);
        getDriver().manage().window().maximize();
        Thread.sleep(1000);
    }

    @Then("page title should be {string}")
    public void titleOfPageVerification(String pageTitle) {
        if (pageTitle.equals("Your store. Home page title")) {
            String actualTitle = getDriver().getTitle();
            assertThat(pageTitle.equals(actualTitle)).isTrue();
        }
        if (pageTitle.equals("Your store. Shopping Cart")) {
            String actualTitle = getDriver().getTitle();
            assertThat(pageTitle.equals(actualTitle)).isTrue();
        }

    }

    @When("user search for product {string}")
    public void searchProduct(String productName) {
        WebElement searchInput = getDriver().findElement(By.id("small-searchterms"));
        if(productName.equals("Lego")){
            System.out.println("User searches product");
            searchInput.sendKeys(productName);
        }
        if(productName.equals("Nike Floral Roshe Customized Running Shoes")){
            searchInput.sendKeys(productName);
        }
    }
    @And("user clicks on {string} button")
    public void userSearchProduct(String buttonToBeClicked) throws InterruptedException {
        if (buttonToBeClicked.equals("Search")) {
            WebElement searchButton = getDriver().findElement(By.xpath("//button[text()='Search']"));
            searchButton.submit();
        }
        if (buttonToBeClicked.equals("go to cart")) {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
            WebElement cartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Go to cart')]")));
            //WebElement cartButton = getDriver().findElement(By.xpath("//button[contains(text(),'Go to cart')]"));
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
        if (buttonToBeClicked.equals("Submit review")) {
            WebElement submitButton = getDriver().findElement(By.xpath("//button[contains(text(),'Submit review')]"));
            submitButton.click();
            Thread.sleep(1000);
        }

    }
    @Then("searchResult Page includes {string}")
    public void searchResultIncludesProduct(String productName) {
        if(productName.equals("Lego")){
            WebElement productToSelect = getDriver().findElement(By.xpath("//div[@class = 'picture']/a[@title='Lego building block']"));
            assertThat(productToSelect.isDisplayed()).isTrue();
        }
        if(productName.equals("shoes")){
            WebElement productToSelect = getDriver().findElement(By.xpath("//div[@class = 'picture']/a[@title = 'Show details for Nike Floral Roshe Customized Running Shoes']"));
            assertThat(productToSelect.isDisplayed()).isTrue();
        }
    }
    @Then("click on the product {string}")
    public void clickProduct(String productName) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        //wait.until(ExpectedConditions.presenceOfElementLocated((By)getDriver().findElement(productToIdMapping.get(productName))));
        WebElement searchProduct = getDriver().findElement(productToIdMapping.get(productName));
        wait.until(ExpectedConditions.elementToBeClickable(searchProduct)).click();
        System.out.println("User redirected to product page");

    }
    @And("user adds quantity of {int} for the product")
    public void selectQuantityForTheProduct(int num) {
        WebElement quantityOfProduct = getDriver().findElement(By.id("product_enteredQuantity_49"));
        quantityOfProduct.clear();
        quantityOfProduct.sendKeys(String.valueOf(num));
    }

    @When("User adds product to cart")
    public void addToCartTheProduct() {
        WebElement addToCart = getDriver().findElement(By.xpath("//div[@class = 'add-to-cart-panel']/button[@class = 'button-1 add-to-cart-button']"));
        WebElement button = getDriver().findElement(By.xpath("//div[@class = 'add-to-cart']"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(button).click().perform();
        addToCart.click();
        System.out.println("Product added to cart");
    }
    @Then("{string} message appears on top of the page")
    public void verifyMessageIsDisplayed(String messageDisplayed) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement successMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(),'added')]")));
        System.out.println("Success message appears");
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
        WebElement email = getDriver().findElement(By.xpath("//input[@id='Email']"));
        email.sendKeys("tester1@gmail.com");
        WebElement password = getDriver().findElement(By.xpath("//input[@id='Password']"));
        password.sendKeys("111111");
    }
    @Given("user is on Login page")
    public void userIsOnLoginPage() {
        WebElement login = getDriver().findElement(By.xpath("//a[contains(text(),'Log in')]"));
        login.click();
    }
    @And("clicks on login button")
    public void clicksOnLoginButton() {
        WebElement loginButton = getDriver().findElement(By.xpath("//button[contains(text(),'Log in')]"));
        loginButton.click();
    }
    @And("added product {string} should appear in shopping cart")
    public void addedProductShouldAppearInShoppingCart(String addedProduct) {
        System.out.println("Enters shopping cart page");
        WebElement cartTable = getDriver().findElement(By.xpath("//table[@class='cart']//tbody"));
        System.out.println("gets the element");
        java.util.List<WebElement> rows = cartTable.findElements(By.tagName("tr"));
        System.out.println(rows.size());
        String itemName="";
        String lastItemPrice = "";
        if(!rows.isEmpty()){
            System.out.println("row not empty");
            for (int i = 0; i < rows.size(); i++) {
                WebElement row = rows.get(i);
                //System.out.println(row.getText());
                java.util.List<WebElement> columns = row.findElements(By.tagName("td"));
                itemName = columns.get(2).getText();
                if(itemName.contains(addedProduct)){
                    String pdt = itemName.substring(0);
                    System.out.println("pdt "  + pdt);
                    break;
                }
                else{
                    assertThat(itemName).contains(addedProduct);
                }
            }
        }
    }
    @And("user selects size, color and print for the product")
    public void userSelectsSizeColorAndPrintForTheProduct() {
        size = getDriver().findElement(By.cssSelector("#product_attribute_6"));
        Select sizeDropdown = new Select(size);
        sizeDropdown.selectByIndex(1);
        WebElement print = getDriver().findElement(By.xpath("//li[@data-attr-value='19']"));
        print.click();
        WebElement color = getDriver().findElement(By.cssSelector("#product_attribute_7"));
        Select colorDropdown = new Select(color);
        colorDropdown.selectByIndex(2);
    }
}




