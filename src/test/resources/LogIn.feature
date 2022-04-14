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
@tag
Feature: User Account 
	#	User can create account and register for the service.
	Background: three user, employee, manager, and department head
	is looking to register an account.

  Scenario: successful register
    Given the user is on the login page
    When the user enters their first name
    And the user enters their last name
    And the user enters a username
    And the user enters a password
    And the user enters his/her manager ID
    And the user select his department
    And the user clicks the register button
    Then an Alert will tell you that you register successfully

  Scenario: username already existed
    Given the user is on the login page
    When the user enters their first name
    And the user enters their last name
    And the user enters a username
    And the user enters a password
    And the user enters his/her manager ID
    And the user select his department
    And the user clicks the register button
    Then an Alert will tell you that your username has been taken
    
  Scenario: successful login
    Given the user is on the login page
    When the user enters a username
    And the user enters a password
    And the user clicks the login button
    Then the page will go into request center page.
    
	Scenario: incorrect credential
    Given the user is on the login page
    When the user enters a username
    And the user enters a password
    And the user clicks the login button
    Then an alert will shows up with the message "incorrect credential"


