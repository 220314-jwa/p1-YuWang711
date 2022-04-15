package Selenium.Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.Select;


public class loginPage {
	WebDriver driver;
	
	@FindBy(id="username")
	WebElement usernameLoginInput;
	@FindBy(id="password")
	WebElement passwordLoginInput;
	@FindBy(id="logInBtn")
	WebElement logInBtn;
	@FindBy(id="firstName")
	WebElement firstName;
	@FindBy(id="lastName")
	WebElement lastName;
	@FindBy(id="register-username")
	WebElement usernameRegisterInput;
	@FindBy(id="register-password")
	WebElement passwordRegisterInput;
	@FindBy(id="managerId")
	WebElement managerID;
	@FindBy(id="departmentId")
	WebElement departmentId;
	@FindBy(id="registerBtn")
	WebElement registerBtn;
	
	
	
	public loginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	public void navigateTo() {
		String strUrl = driver.getCurrentUrl();
		if(strUrl == "http://localhost:8080/requestCenter.html") {
			LogOut();
			Wait<WebDriver> fluentWait = new FluentWait<>(driver)
					.withTimeout(Duration.ofSeconds(2))
					.pollingEvery(Duration.ofMillis(500));
			fluentWait.until(ExpectedConditions.elementToBeClickable(By.id("logInBtn")));
		}
		driver.get("http://localhost:8080/");
	}
	public void inputLogInUsername(String username) {
		usernameLoginInput.sendKeys(username);
	}
	public void inputLogInPassword(String password) {
		passwordLoginInput.sendKeys(password);
	}
	public void inputRegisterFirstName(String firstname) {
		firstName.sendKeys(firstname);
	}
	public void inputRegisterLastName(String lastname) {
		lastName.sendKeys(lastname);
	}
	public void inputRegisterUsername(String username) {
		usernameRegisterInput.sendKeys(username);
	}
	public void inputRegisterPassword(String password) {
		passwordRegisterInput.sendKeys(password);
	}
	public void inputRegisterManagerId(String managerId) {
		managerID.sendKeys(managerId);
	}
	public void inputRegisterDepartmentId(String dptId) {
		Select dropDept = new Select(departmentId);
		dropDept.selectByValue(dptId);
	}
	public void logInBtn() {
		logInBtn.click();
	}
	public void inputRegisterBtn() {
		registerBtn.click();
	}
	
	public String getAlertMessage() {
		Wait<WebDriver> fluentWait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofMillis(500));
		fluentWait.until(ExpectedConditions.alertIsPresent());

		return driver.switchTo().alert().getText();
	}
	
	public void AcceptAlert() {
		driver.switchTo().alert().accept();
	}
	
	public String getNextURL() {
		Wait<WebDriver> fluentWait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(2))
				.pollingEvery(Duration.ofMillis(500));
		fluentWait.until(ExpectedConditions.elementToBeClickable(By.id("requestCenterTab")));
		String strUrl = driver.getCurrentUrl();
		return strUrl;
	}
	
	public void LogOut() {
		Wait<WebDriver> fluentWait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(2))
				.pollingEvery(Duration.ofMillis(500));
		fluentWait.until(ExpectedConditions.elementToBeClickable(By.id("logOut")));
		WebElement logoutBtn = driver.findElement(By.id("logOut"));
		logoutBtn.click();
	}
}
