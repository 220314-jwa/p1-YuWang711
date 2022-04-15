package Selenium.Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

public class requestPage {
	WebDriver driver;
	
	@FindBy(id="eventType")
	WebElement eventType;
	
	@FindBy(id="location")
	WebElement location;
	
	@FindBy(id="costs")
	WebElement costs;
	
	@FindBy(id="eventDate")
	WebElement eventDate;
	
	@FindBy(id="description")
	WebElement description;
	
	@FindBy(id="requestCenterTab")
	WebElement requestCenter;
	
	@FindBy(id="pendingRequestTab")
	WebElement pendingRequest;
	
	@FindBy(id="createRequestTab")
	WebElement createRequest;
	
	@FindBy(id="logOut")
	WebElement logOut;
	
	@FindBy(id="submitARequest")
	WebElement submitRequest;

	public requestPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	public String getCurrentURL() {
		String strUrl = driver.getCurrentUrl();
		return strUrl;
	}
	public void inputEventType(String eventTypeID) {
		Select dropEventType = new Select(eventType);
		dropEventType.selectByValue(eventTypeID);
	}
	public void inputLocation(String slocation) {
		location.sendKeys(slocation);
	}
	public void inputCosts(String scosts) {
		costs.sendKeys(scosts);
	}
	public void inputEventDate(String seventDate) {
		eventDate.sendKeys(seventDate);
	}
	public void inputDescription(String sdescription) {
		description.sendKeys(sdescription);
	}
	public void clickRequestCenter() {
		requestCenter.click();
	}
	public void clickPendingRequest() {
		pendingRequest.click();
	}
	public void clickCreateRequest() {
		createRequest.click();
	}
	public void clickSubmitRequest() {
		submitRequest.click();
	}
	
	public String getRequestAlertMessage() {
		Wait<WebDriver> fluentWait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(2))
				.pollingEvery(Duration.ofMillis(500));
		fluentWait.until(ExpectedConditions.alertIsPresent());
		return driver.switchTo().alert().getText();
	}
	
	public void clickRequest(String id) {
		driver.findElement(By.id("request"+id)).click();
	}
	
	public void clickAccept(String id) {
		driver.findElement(By.id("Accept"+id)).click();
	}
	public void clickReject(String id) {
		driver.findElement(By.id("Reject"+id)).click();
	}
	
	public String findRequestById(String id) {
		Wait<WebDriver> fluentWait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(4))
				.pollingEvery(Duration.ofMillis(500));
		fluentWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("request"+id)));
		return driver.findElement(By.id("request"+id)).getAttribute("id");
	}
	public String findStatusById(String id) {
		Wait<WebDriver> fluentWait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(2))
				.pollingEvery(Duration.ofMillis(500));
		fluentWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("status"+id)));
		return driver.findElement(By.id("status"+id)).getText();
	}
	
	public void acceptAlert() {
		driver.switchTo().alert().accept();
	}
}
