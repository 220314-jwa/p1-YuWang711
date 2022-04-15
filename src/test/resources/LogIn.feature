#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template

@login
Feature: User Account 
	#	User can create account and register for the service.
	Background: three user, employee, manager, and department head
	is looking to register an account.

  Scenario: successful register
    Given the user is on the login page
    When the user enters their first name in register form
    And the user enters their last name in register form
    And the user enters username "usertest1" in register form
    And the user enters password "usertest1" in register form
    And the user enters their manager ID in register form
    And the user select his department in register form
    And the user clicks the register button
    Then an Alert will tell you that you register successfully

  Scenario: username already existed
    Given the user is on the login page
    When the user enters their first name in register form
    And the user enters their last name in register form
    And the user enters username "usertest1" in register form again
    And the user enters password "usertest1" in register form again
    And the user enters their manager ID in register form
    And the user select his department in register form
    And the user clicks the register button
    Then an Alert will tell you that your username has been taken
 
 	Scenario: user does not exist
    Given the user is on the login page
    When the user enters username "usernotexist" in login form for user does not exist
    And the user enters password "usernotexist" in login form for user does not exist
    And the user clicks the login button
    Then an alert will shows up with the message "User does not exist"
 
  	Scenario: incorrect credential
    Given the user is on the login page
    When the user enters username "usertest1" in login form for inorrect credential
    And the user enters password "usernotexist" in login form for inorrect credential
    And the user clicks the login button
    Then an alert will shows up with the message "Incorrect Username or Password"
 
  Scenario: successful login
    Given the user is on the login page
    When the user enters username "usertest1" in login form
    And the user enters password "usertest1" in login form
    And the user clicks the login button
    Then the page will go into request center page.
    
  Scenario: Submit a request
  	Given the user is log in
  	When the user click on create request button on the top left
  	And the user fill out the form
  	And the user click submit request
  	Then an alert will say "request submit successfully" and the request ID
  	
  	
  Scenario: View Request
  	Given the user is log in
  	When the user click on "View Your Requests" button on the top left
  	Then request will shows up with their ID
  	
  Scenario: Manager View Submitted Request
    Given the user is log in
  	When the user click on "Pending on Your Response" button on the top left
  	Then requests from employees that has your user ID 
  	And their manager ID and the request that are pending on your response will show up

  Scenario: User Accept a Request
    Given the user is log in
  	When the user click on "Pending on Your Response" button on the top left
  	And the user clicks on a request
  	And the user clicks on accept 
  	Then the request will be updated and your pending request will be accepted

  Scenario: User Reject a Request
    Given the user is log in
  	When the user click on "Pending on Your Response" button on the top left
  	And the user clicks on a request
  	And the user clicks on reject 
  	Then the request will be updated and your pending request will be rejected
  	
  Scenario: Department Head View Submitted Request
    Given the user is log in
  	When the user click on "Pending on Your Response" button on the top left
  	Then requests from employees that are in your department 
  	And the requests that are pending on your response will show up
