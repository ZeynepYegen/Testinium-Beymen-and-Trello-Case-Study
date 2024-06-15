package com.testinium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductPage {
    private WebDriver driver;
    @FindBy(className = "o-productDetail__description")
    public WebElement productName;

    @FindBy(className = "m-price__new")
    public WebElement fullPrice;

    @FindBy(className = "m-price__lastPrice")
    private WebElement discountedPrice;

    @FindBy(className = "m-variation")
    private WebElement productSizes;

    @FindBy(xpath = "//*[@id='addBasket']")
    private WebElement addToCartButton;

    public ProductPage(WebDriver driver) {
        this.driver = driver;

        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public static String parsePrice(String price) {
        // Eğer metin içinde "," bulunmuyorsa, sonuna ",00" ekley
        price = price.split(" ")[0];
        if (!price.contains(",")) {
            return price + ",00";
        }
        // TODO remove comments
        // "," zaten varsa, metni olduğu gibi bırak
        return price;
    }

    private WebElement findFirstAvailableSize() {
        List<WebElement> availableSizes = productSizes.findElements(By.cssSelector("span:not(.m-variation__item.-disabled)"));
        return availableSizes.get(0);
    }

    // TODO
    public void clickOnAvailableProduct() throws InterruptedException {
        WebElement size = findFirstAvailableSize();
        if (size != null) size.click();
        Thread.sleep(2000L);
    }

    public void clickAddToCartButton() throws InterruptedException {
        addToCartButton.click();
        Thread.sleep(2000L);
    }

    public void navigateToMyCartPage() throws InterruptedException {
        driver.get("https://www.beymen.com/cart");
        Thread.sleep(2000L);
    }

    public String finalPrice() {
        try {
            return addCommaIfNotPresent(discountedPrice.getText().split("\n")[1].trim());
        } catch (Exception e) {
            return addCommaIfNotPresent(fullPrice.getText());
        }

    }

    private static String addCommaIfNotPresent(String fraction) {
        // Eğer metin içinde "," bulunmuyorsa, sonuna ",00" ekleyin
        fraction = fraction.split(" ")[0];
        if (!fraction.contains(",")) {
            return fraction + ",00";
        }
        // "," zaten varsa, metni olduğu gibi bırakın
        return fraction;
    }
}
