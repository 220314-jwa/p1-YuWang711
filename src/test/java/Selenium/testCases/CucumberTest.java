package Selenium.testCases;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


import io.cucumber.core.cli.Main;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;



public class CucumberTest {
	static WebDriver driver;
	static Selenium.Pages.loginPage loginPage;
	static Selenium.Pages.requestPage requestPage;
	
	static String requestID = "";
	@BeforeAll
	public static void setUp() {
		File file = new File("src/test/resources/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		
		driver = new ChromeDriver();
		loginPage = new Selenium.Pages.loginPage(driver);
		requestPage = new Selenium.Pages.requestPage(driver);
	}
	
	@AfterAll
	public static void closeDriver() {
		//loginPage.LogOut();
		//driver.quit();
	}
	
	
	@Given("the user is on the login page")
	public void the_user_is_on_the_login_page() {
		loginPage.navigateTo();
	}

	@When("the user enters their first name in register form")
	public void the_user_enters_their_first_name() {
		loginPage.inputRegisterFirstName("usertest2");
	}

	@When("the user enters their last name in register form")
	public void the_user_enters_their_last_name() {
		loginPage.inputRegisterLastName("usertest2");
	}
	
	@When("the user enters username {string} in register form")
	public void the_user_enters_username_in_register_form(String string) {
		loginPage.inputRegisterUsername(string);
	}

	@When("the user enters password {string} in register form")
	public void the_user_enters_password_in_register_form(String string) {
		loginPage.inputRegisterPassword(string);
	}

	@When("the user enters username {string} in register form again")
	public void the_user_enters_username_in_register_form_again(String string) {
		loginPage.inputRegisterUsername(string);
	}

	@When("the user enters password {string} in register form again")
	public void the_user_enters_password_in_register_form_again(String string) {
		loginPage.inputRegisterPassword(string);
	}

	@When("the user enters their manager ID in register form")
	public void the_user_enters_his_her_manager_id() {
		loginPage.inputRegisterManagerId("1");
	}

	@When("the user select his department in register form")
	public void the_user_select_his_department() {
		loginPage.inputRegisterDepartmentId("1");
	}

	@When("the user clicks the register button")
	public void the_user_clicks_the_register_button() {
		loginPage.inputRegisterBtn();
	}

	@Then("an Alert will tell you that you register successfully")
	public void an_alert_will_tell_you_that_you_register_successfully() {
		String AlertMessage = loginPage.getAlertMessage();
	    assertTrue(AlertMessage.contains("register successfully"));
		loginPage.AcceptAlert();
	}

	@Then("an Alert will tell you that your username has been taken")
	public void an_alert_will_tell_you_that_your_username_has_been_taken() {
		String AlertMessage = loginPage.getAlertMessage();
	    assertTrue(AlertMessage.contains("username has been taken"));
		loginPage.AcceptAlert();
	}
	@When("the user enters username {string} in login form for user does not exist")
	public void the_user_enters_username_in_login_form_for_user_does_not_exist(String string) {
		loginPage.inputLogInUsername(string);
	}

	@When("the user enters password {string} in login form for user does not exist")
	public void the_user_enters_password_in_login_form_for_user_does_not_exist(String string) {
		loginPage.inputLogInPassword(string);
	}

	@When("the user enters username {string} in login form for inorrect credential")
	public void the_user_enters_username_in_login_form_for_inorrect_credential(String string) {
		loginPage.inputLogInUsername(string);
	}

	@When("the user enters password {string} in login form for inorrect credential")
	public void the_user_enters_password_in_login_form_for_inorrect_credential(String string) {
		loginPage.inputLogInPassword(string);
	}
	@When("the user enters username {string} in login form")
	public void the_user_enters_username_in_login_form(String string) {
		loginPage.inputLogInUsername(string);
	}

	@When("the user enters password {string} in login form")
	public void the_user_enters_password_in_login_form(String string) {
		loginPage.inputLogInPassword(string);
	}
	@When("the user clicks the login button")
	public void the_user_clicks_the_login_button() {
		loginPage.logInBtn();
	}

	@Then("the page will go into request center page.")
	public void the_page_will_go_into_request_center_page() {
	    assertEquals("http://localhost:8080/requestCenter.html",loginPage.getNextURL());
	}

	@Then("an alert will shows up with the message {string}")
	public void an_alert_will_shows_up_with_the_message(String string) {
		String AlertMessage = loginPage.getAlertMessage();
	    assertTrue(AlertMessage.contains(string));
		loginPage.AcceptAlert();

	}
	@Given("the user is log in")
	public void the_user_is_log_in() {
	    assertEquals("http://localhost:8080/requestCenter.html",requestPage.getCurrentURL());
	}
	@When("the user click on create request button on the top left")
	public void the_user_click_on_create_request_button_on_the_top_left() {
	    // Write code here that turns the phrase above into concrete actions
		requestPage.clickCreateRequest();
	}

	@When("the user fill out the form")
	public void the_user_fill_out_the_form() {
		requestPage.inputEventType("1");
		requestPage.inputEventDate("04/14/2022");
		requestPage.inputCosts("100");
		requestPage.inputLocation("Mars");
		requestPage.inputDescription("shooting starts");
	}

	@When("the user click submit request")
	public void the_user_click_submit_request() {
		requestPage.clickSubmitRequest();
	}

	@Then("an alert will say {string} and the request ID")
	public void an_alert_will_say_and_the_request_id(String string) {
	    StringBuilder sb = new StringBuilder(requestPage.getRequestAlertMessage());
	    String from ="Request Submit Succesffuly! Request ID : ";
	    String to = "";
	    sb = sb.replace(sb.indexOf(from), sb.indexOf(from) + from.length(), to);
	    requestID = sb.toString();
	    assertTrue(Integer.parseInt(requestID)>0);
	    requestPage.acceptAlert();
	}

	@When("the user click on {string} button on the top left")
	public void the_user_click_on_button_on_the_top_left(String string) {
		if(string.equals("Pending on Your Response")) {
			requestPage.clickPendingRequest();
		} else if(string.equals("View Your Requests")){
			requestPage.clickRequestCenter();
		}
	}

	@Then("request will shows up with their ID")
	public void request_will_shows_up_with_their_id() {
		String ID = requestPage.findRequestById(requestID);
		assertTrue(ID.equals("request"+requestID));
	}

	@When("the user clicks on a request")
	public void the_user_clicks_on_a_request() {
		requestPage.clickRequest(requestID);
	}

	@When("the user clicks on accept")
	public void the_user_clicks_on_accept() {
		requestPage.clickAccept(requestID);
	}

	@Then("the request will be updated and your pending request will be accepted")
	public void the_request_will_be_updated_and_your_pending_request_will_be_accepted() {
		String acceptText = requestPage.findStatusById(requestID);
		assertNotEquals("Pending Manager Approval",acceptText);
	}
	@Then("the request will be updated and your pending request will be rejected")
	public void the_request_will_be_updated_and_your_pending_request_will_be_rejected() {
		String rejectText = requestPage.findStatusById(requestID);
		assertNotEquals("Pending Manager Approval",rejectText);
	}

	@When("the user clicks on reject")
	public void the_user_clicks_on_reject() {
		requestPage.clickReject(requestID);
	}

	@Then("requests from employees that has your user ID")
	public void requests_from_employees_that_has_your_user_id() {
		String ID = requestPage.findRequestById(requestID);
		assertTrue(ID.equals("request"+requestID));
	}

	@Then("their manager ID and the request that are pending on your response will show up")
	public void their_manager_id_and_the_request_that_are_pending_on_your_response_will_show_up() {
		assertEquals(requestID,requestPage.findRequestById(requestID));
	}

	@Then("the requests that are pending on your response will show up")
	public void the_requests_that_are_pending_on_your_response_will_show_up() {
		assertEquals(requestID,requestPage.findRequestById(requestID));
	}

	@Then("requests from employees that are in your department")
	public void requests_from_employees_that_are_in_your_department() {
		assertEquals(requestID,requestPage.findRequestById(requestID));
	}

}
