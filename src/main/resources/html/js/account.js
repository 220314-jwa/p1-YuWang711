

let TRMS_url = 'http://localhost:7077/'

async function logIn(){
	let username = document.getElementById('username').value;
	let password = document.getElementById('password').value;
	
	let credentials = {
		'username':username,
		'password':password
	};
	console.log(JSON.stringify(credentials));
	alert(TRMS_url + 'users/auth');
	let response = await fetch(TRMS_url + 'users/auth', {method:'POST', body: JSON.stringify(credentials)});
	if(response.status === 200){
		//let token = await response.text();
		alert("it works");
	} else if(response.status === 404){
		let msg = await response.text();
	}
};

async function register(){

	let firstName = document.getElementById('firstName').value;
	let lastName = document.getElementById('lastName').value;
	let username = document.getElementById('register-username').value;
	let password = document.getElementById('register-password').value;

	let credentials = {
		'firstName':firstName,
		'lastName':lastName,
		'username':username,
		'password':password
	};
	console.log(JSON.stringify(credentials));
	alert(TRMS_url + 'users/register');
	let response = await fetch( (TRMS_url + 'users/register'), {
    	method: 'POST', 
    	body: JSON.stringify(credentials) 
  	});
	alert(response.status);
	if(response.status === 200){
		
	} else if (response.status === 400){
		alert("invalid input");
	}

};



async function printAll(){
	
};