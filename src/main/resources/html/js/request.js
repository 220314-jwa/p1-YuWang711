
function init(){
  getEventType();
  checkLogin();
}

async function checkLogin(){
	let userId = sessionStorage.getItem('Auth-Token');
  if(userId == null){ LogOut();}
	let httpResp = await fetch(window.location.origin+"/users/"+userId);
	if(httpResp && httpResp.status === 200){
	} else {
    LogOut();
  }
}

async function getEventType(){
  let response = await fetch(window.location.origin+'/requests/getEventTypes',{method:'POST'});
	let eventTypes = await response.json();
	let eventTypeEle = document.getElementById("eventType");
	for(let eventType of eventTypes){
		var opt = document.createElement('option');
		opt.value = eventType.eventTypeID;
		opt.innerHTML = eventType.eventType;
		eventTypeEle.appendChild(opt);	
	}
}



function LogOut(){
  sessionStorage.removeItem("Auth-Token");
  window.location.replace(window.location.origin);
}

function openEventTab(evt, eventName) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
      tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
      tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(eventName).style.display = "block";
    evt.currentTarget.className += " active";
}

function toggleRequestDisplay(element){
  let request = element.parentElement.getElementsByClassName("request-content")[0];
  if (request.style.display === "none") {
    request.style.display = "block";
  } else {
    request.style.display = "none";
  }
}

let apiURL = window.location.origin;
let requestAPI = "/requests";

let employeeAPI = "/employee";
let managerAPI = "/manager";
let deptHeadAPI = "/deptHead";

let getRequest = "/getRequests";
let submitRequestAPI = "/submitRequest";
let editRequest = "/editRequest";

async function submitRequest(){
  let EventType = document.getElementById("eventType").value;
  let location = document.getElementById("location").value;
  let costs = document.getElementById("costs").value;
  let eventDate = document.getElementById("eventDate").value;
  let description = document.getElementById("description").value;
  let apiCall = requestAPI+employeeAPI+submitRequestAPI;
  let credentials = {
    'submitterID': sessionStorage.getItem('Auth-Token'),
    'eventTypeID':EventType,
    'statusID':"1",
    'location':location,
    'costs':costs,
    'eventDate':eventDate,
    'description':description,
    'submittedAt':new Date().toLocaleTimeString()
  }
  let response = await fetch(apiCall, {method:'POST',  body: JSON.stringify(credentials)});
  if(response.status === 200){
    let requestID = await response.json();
    alert("Request Submit Succesffuly! Request ID : "+requestID);
    document.getElementById("submitForm").reset();
  } else{
    let msg = await response.text();
  }
}

function refresh(){
  getRequestByDeptHeadID();
  getRequestByManagerID();
}

async function editRequestByRequesetID(ele, status,requestID){
  let apiCall = "";
  if(ele.className == "manager-request"){
    apiCall = requestAPI+managerAPI+editRequest;
  } else if (ele.className == "department-request"){
    apiCall = requestAPI+deptHeadAPI+editRequest;
  }
  let credentials = {};
  if(status == "Accept"){
    if(ele.className == "manager-request"){
      credentials = {
        "requestID":requestID,
        "statusID": "2"
      }
    } else if (ele.className == "department-request"){
      credentials = {
        "requestID":requestID,
        "statusID": "4"
      }
    }
  } else if (status == "Reject"){
    credentials = {        
      "requestID":requestID,
      "statusID": "5"
    }
  }
  let response = await fetch(apiCall, {method:'POST',  body: JSON.stringify(credentials)});
  if(response.status === 200){
    alert("Request Status Update Succesffuly!");
    refresh();
  } else{
    let msg = await response.text();
  }
}

async function getRequestByEmployeeID(){
  let apiCall = requestAPI+employeeAPI+getRequest;
  var ele = "viewRequest";
  let credentials = {
    "employeeID": sessionStorage.getItem('Auth-Token')
  };
  let response = await fetch(apiCall, {method:'POST',  body: JSON.stringify(credentials)});

  if(response.status === 200){
    let data = await response.json();
    populateRequest(data, ele);
  } else{
    let msg = await response.text();
  }
}

async function getRequestByDeptHeadID(){
  let apiCall = requestAPI+deptHeadAPI+getRequest;
  var ele = "department-request";
  let credentials = {
    "deptHeadID": sessionStorage.getItem('Auth-Token')
  };

  let response = await fetch(apiCall, {method:'POST',  body: JSON.stringify(credentials)});

  if(response.status === 200){
    let data = await response.json();
    populateRequest(data, ele);
  } else{
    let msg = await response.text();
  }

}

async function getRequestByManagerID(){
  let apiCall = requestAPI+managerAPI+getRequest;
  var ele = "manager-request";
  let credentials = {
    "managerID": sessionStorage.getItem('Auth-Token')
  };

  let response = await fetch(apiCall, {method:'POST',  body: JSON.stringify(credentials)});

  if(response.status === 200){
    let data = await response.json();
    populateRequest(data, ele);
  } else{
    let msg = await response.text();
  }

}

function populateRequest(jsonRequests, parent){
  let parentEle = document.getElementById(parent);
  parentEle.innerHTML = "";
  for(let request of jsonRequests){
    let requestContainer = createDiv("request-container");

    let elementText = '<div class="request-id-label" id=\'request'+request.requestID+'\' onclick="toggleRequestDisplay(this);">'+
    request.requestID +
    '</div>\
    <div class="request-content" style="display:none;">\
    <div class="request-row">\
      <div class="request-label-container request">\
        <div class="request-label request">Request ID:</div>\
        <div class="request-info request" id="requestID">' + request.requestID + '</div>\
      </div>\
    </div>\
    <div class="request-row" >\
    <div class="request-label-container request">\
      <div class="request-label request">submitter ID:</div>\
      <div class="request-info request" id="submitterID">' +request.submitterId+'</div>\
    </div>\
    <div class="request-label-container request">\
      <div class="request-label request">submitter name:</div>\
      <div class="request-info request" id="submitterName">testing</div>\
    </div>\
</div>\
<div class="request-row" >\
<div class="request-label-container request">\
  <div class="request-label request">event type:</div>\
  <div class="request-info request " id="eventType">'+request.eventTypeId.eventType+'</div>\
</div>\
<div class="request-label-container request">\
  <div class="request-label request">event date:</div>\
  <div class="request-info request" id="eventDate">'+request.eventDate+'</div>\
</div>\
</div>\
<div class="request-row" >\
<div class="request-label-container request">\
  <div class="request-label request">event cost:</div>\
  <div class="request-info request" id="eventCost">'+request.cost+'</div>\
</div>\
<div class="request-label-container request">\
  <div class="request-label request">location:</div>\
  <div class="request-info request" id="location">'+request.location+'</div>\
</div>\
</div>\
<div class="request-row" >\
<div class="request-label-container request">\
  <div class="request-label request">status:</div>\
  <div class="request-info request" id=\'status'+request.requestID+'\'>'+request.statusId.statusType+'</div>\
</div>\
</div>\
<div class="request-row" >\
<div class="request-label-container request">\
  <div class="request-label request">description:</div>\
  <div class="request-info request" id="description">'+request.description+'</div>\
</div>\
</div>\
';
    if(parent === "manager-request" || parent === "department-request"){
      elementText =  elementText + '<div class="request-row">\
      <button class=\''+ parent + '\' id=\'Accept'+request.requestID+'\' onclick="editRequestByRequesetID(this,\'Accept\',\''+request.requestID+'\')">Accept</button>\
      <button class=\''+ parent + '\' id=\'Reject'+request.requestID+'\' onclick="editRequestByRequesetID(this,\'Reject\',\''+request.requestID+'\')">Reject</button>\
      </div>\
      ';
    }
    elementText =  elementText + '</div>';
    requestContainer.innerHTML = elementText;
    parentEle.appendChild(requestContainer);
  }
}

function createDiv(className){
  let divEle = document.createElement("div");
  divEle.className = className;
  return divEle;
}

init();
