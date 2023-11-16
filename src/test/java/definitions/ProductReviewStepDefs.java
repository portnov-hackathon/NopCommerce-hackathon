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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.assertj.core.api.Assertions.*;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import static support.Hooks.getDriver;

public class ProductReviewStepDefs {

    @And("verify Add your review link is visible")
    public void verifyReviewLink() {

            WebElement addReviewLink = getDriver().findElement(By.xpath("//a[text()='Add your review']"));
            assertThat(addReviewLink.isDisplayed()).isTrue();

        }

    @When("user clicks on {string} link")
    public void userClicksOnLink(String addReview) {
        WebElement addReviewLink = getDriver().findElement(By.xpath("//a[text()='Add your review']"));
        if(addReviewLink.getText().equals(addReview)){
            addReviewLink.click();
        }
        System.out.println("User clicks on add review link");
    }

    @Then("{string} message appears on Product Review page")
    public void messageAppearsOnProductReviewPage(String message) {
        if(message.contains("Only registered users")){
            WebElement reviewMessage = getDriver().findElement(By.xpath("//li[contains(text(),'Only registered users can write reviews')]"));
            assertThat(reviewMessage.isDisplayed()).isTrue();
            System.out.println("Only registered users allowed to review");
        }
        if(message.contains("successfully added.")){
          WebElement successMessage = getDriver().findElement(By.xpath("//div[contains(text(),'successfully added.')]"));
          assertThat(successMessage.isDisplayed()).isTrue();
          System.out.println("Product review successfully added");
        }
    }

    @Then("user proceeds to {string} page")
    public void userProceedsToProductReviewPage(String titleOfPage) {
        String title = getDriver().getTitle();
       assertThat(title.contains(titleOfPage)).isTrue();
    }

    @And("user provides Review title , Review text and Rating for the product")
    public void userProvidesAndForTheProduct() {
        System.out.println("entered review fieldset section");
//        WebElement title = getDriver().findElement(By.xpath("//input[@id='AddProductReview_Title']"));
//        title.sendKeys("good");
        WebDriverWait wait = new WebDriverWait(getDriver(),Duration.ofSeconds(20));

        WebElement title= wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='AddProductReview_Title']")));
title.sendKeys("review for the lego");
        WebElement text= wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@id='AddProductReview_ReviewText']")));
        text.sendKeys("text area for the review");
        WebElement rating= wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='addproductrating_4']")));
        rating.click();






    }
}

