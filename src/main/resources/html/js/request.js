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

let requestAPI = "/request";

let employeeAPI = "/employee";
let managerAPI = "/manager";
let deptHeadAPI = "/deptHead";

let getRequest = "/getRequests";
let submitRequest = "/submiteRequest";
let editRequest = "/editRequest";

employeeID = "";

async function getRequestByEmployeeID(){

  let apiCall = requestAPI+employeeAPI+getRequest;
  let ele = document.getElementById("requestCenter");
  let credentials = {
    "employeeID": employeeID
  };

  let response = await fetch(apiCall, {method:'POST',  body: JSON.stringify(credentials)});

  if(response.status === 200){
    let data = await response.json();
    populateRequest(data, ele);
  } else{
    let msg = await response.text();
  }

}

function populateRequest(jsonRequests, element){
  for(let i = 0; i < jsonRequests.length; i++){
    console.log(jsonRequests[0]);
  }
}
