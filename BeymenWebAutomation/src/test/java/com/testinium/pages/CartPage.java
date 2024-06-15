package com.testinium.pages;

import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static com.testinium.helpers.WebDriverWait.waitUntilElementVisible;

public class CartPage {
    private WebDriver driver;
    @FindBy(css = ".m-orderSummary__item.-grandTotal .m-orderSummary__value")
    private WebElement grandTotalPrice;
    @FindBy(id = "quantitySelect0-key-0")
    private WebElement quantityDropdown;

    @FindBy(id = "removeCartItemBtn0-key-0")
    private WebElement emptyCart;

    @FindBy(id = "emtyCart")
    private WebElement cartIsEmptyInfoBox;

    public CartPage(WebDriver driver) {
        this.driver = driver;

        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public String parsePrice() {
        // strip currency at the end of grand total price
        return grandTotalPrice.getText().split(" ")[0];
    }

    /**
     * @param quantity Increase quantity by the amount passed
     * @return boolean if increasing quantity is success or failed(because of critical stock)
     */
    public boolean increaseQuantity(int quantity) throws InterruptedException {
        Select select = new Select(quantityDropdown);
        List<WebElement> options = select.getOptions();

        // Perform a null check
        if (isStockAvailable(quantity, options)) {
            select.selectByValue(String.valueOf(quantity));
            Thread.sleep(2000L);
            return true;
        } else {
            throw new NotFoundException();
        }

    }

    private boolean isStockAvailable(int quantity, List<WebElement> options) {
        for (WebElement option : options) {
            if (String.valueOf(quantity).equals(option.getAttribute("value"))) {
                return true;
            }
        }
        return false;
    }

    public void emptyCart() throws InterruptedException {
        waitUntilElementVisible(driver, emptyCart).click();
        Thread.sleep(2000L);
    }

    public boolean isCartEmpty() {
        return waitUntilElementVisible(driver, cartIsEmptyInfoBox).isDisplayed();
    }

    public String getSelectedQuantity() throws InterruptedException {
        Select select = new Select(quantityDropdown);
        Thread.sleep(2000L);
        return select.getFirstSelectedOption().getAttribute("value");
    }
}
