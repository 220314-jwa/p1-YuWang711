/*
	need to transform json into the script.
*/


let TRMS_url = 'http://localhost:8080/'

async function init(){
	await getDepartment();
	await checkLogin();
}

async function getDepartment(){
	let response = await fetch('http://localhost:8080/users/getDepartments',{method:'POST'});
	let department = await response.json();
	let departmentEle = document.getElementById("departmentId");
	console.log(department);
	for(let dept of department){
		var opt = document.createElement('option');
		opt.value = dept.department_id;
		opt.innerHTML = dept.depardment_Name;
		departmentEle.appendChild(opt);	
	}
}

function goToRequest() {      
	window.location.replace(window.location.origin + "/requestCenter.html");
};

async function checkLogin(){
	let userId = sessionStorage.getItem('Auth-Token');
	let httpResp = await fetch(window.location.origin+"/users/"+userId);
	if(httpResp && httpResp.status === 200){
		goToRequest();
	}
}

async function logIn(){
	let username = document.getElementById('username').value;
	let password = document.getElementById('password').value;
	
	let credentials = {
		'username':username,
		'password':password
	};
	let response = await fetch(window.location.origin + '/users/auth', {method:'POST', body: JSON.stringify(credentials)});
	console.log(response.status);
	if(response && response.status === 200){
		let loggedInUser = await response.json();
		console.log(loggedInUser);
		sessionStorage.setItem('Auth-Token', loggedInUser);

		await checkLogin();
	} else {
		let msg = await response.text();
	}
};

async function register(){

	let firstName = document.getElementById('firstName').value;
	let lastName = document.getElementById('lastName').value;
	let username = document.getElementById('register-username').value;
	let password = document.getElementById('register-password').value;
	let departmentId = document.getElementById("departmentId").selectedOptions[0].value;
	let managerId = document.getElementById('managerId').value;

	let credentials = {
		'firstName':firstName,
		'lastName':lastName,
		'username':username,
		'password':password,
		'departmentId':departmentId,
		'managerId':managerId
	};

	let response = await fetch( (window.location.origin + '/users/register'), {
    	method: 'POST', 
    	body: JSON.stringify(credentials) 
  	});
	if(response.status === 201){
		document.getElementById("register-form").reset();
		alert("register successfully");
	} else if (response.status === 400){
		alert("invalid input");
	}

};


init();