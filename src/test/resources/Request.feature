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
Feature: Requests Feature
	# user can submit reimbursement request to their
	# manager and department head
	Background: user has already register an account and logged into their account
  Scenario: Submit a request
  	Given the user is log in
  	When the user click on submit a request button on the top left
  	And the user fill out the form
  	And the user click submit request
  	Then an alert will say "request submit successfully" and the request ID

  Scenario: View Request
  	Given the user is log in
  	When the user click on "View Your Requests" button on the top left
  	Then request will shows up with their ID

    Examples: 
      [ Request ID (clickable) 				 ]
      [		request detail							 ]
      [ Another Request ID (clickable) ]
      
      
  Scenario: Manager View Submitted Request
    Given the user is log in
  	When the user click on "Pending on Your Response" button on the top left
  	Then requests from employees that has your user ID and their manager ID 
  		and the request status is pending on your response will show up
      
     Examples: 
      [ Request ID (clickable) 				 ]
      [		request detail							 ]
      [ Another Request ID (clickable) ]
      
  Scenario: User Accept a Request
    Given the user is log in
  	When the user click on "Pending on Your Response" button on the top left
  	And the user clicks on a request
  	And the user clicks on accept 
  	Then the request will be updated and your pending request will also be updated

  Scenario: User Accept a Request
    Given the user is log in
  	When the user click on "Pending on Your Response" button on the top left
  	And the user clicks on a request
  	And the user clicks on reject 
  	Then the request will be updated and your pending request will also be updated
        
  Scenario: Department Head View Submitted Request
    Given the user is log in
  	When the user click on "Pending on Your Response" button on the top left
  	Then requests from employees that are in your department and the request status is
  	 pending on your response will show up
      
     Examples: 
      [ Request ID (clickable) 				 ]
      [		request detail							 ]
      [ Another Request ID (clickable) ]
      
      
      
      