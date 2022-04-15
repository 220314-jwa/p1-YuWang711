@request
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

  Scenario: User Reject a Request
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
      
      
      
      