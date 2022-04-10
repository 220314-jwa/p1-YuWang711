package main;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import exception.RequestSubmittedUnsuccessfully;
import exception.UsernameAlreadyExistsException;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import service.RequestServiceImpl;

public class RequestController {
	private static RequestServiceImpl requestServiceImpl = new RequestServiceImpl();
	
	public static void submitRequest(Context ctx) {
		Map<String,String> credentials = ctx.bodyAsClass(Map.class);
		Request request = new Request();

		request.setSubmitterId(Integer.parseInt(credentials.get("submitterID")));
		request.setEventTypeId(new EventType(Integer.parseInt(credentials.get("eventTypeID")),""));
		request.setStatusId(new Status(Integer.parseInt(credentials.get("statusID")),""));
		request.setEventDate(LocalDate.parse(credentials.get("eventDate")).toString());
		request.setCost(Double.parseDouble(credentials.get("costs")));
		request.setDescription(credentials.get("description"));
		request.setLocation(credentials.get("location"));
		request.setSubmittedAt(credentials.get("submittedAt"));
		try {
			ctx.status(HttpCode.CREATED);
			requestServiceImpl.submitRequest(request);
			ctx.status(200);			
			//Not sure what to return but its fine.
		} catch (RequestSubmittedUnsuccessfully e) {
			//Need to write some homemade exception
			ctx.status(401);
			ctx.result(e.getMessage());
		} 
	}
	
	public static void getRequestsByEmployeeID(Context ctx){
		Map<String,String> credentials = ctx.bodyAsClass(Map.class);
		
		int employeeID = Integer.parseInt(credentials.get("employeeID"));
		
		Set<Request> requests = requestServiceImpl.getRequestsByEmployeeID(employeeID);
		System.out.println("getrequests");
		ctx.json(requests);
		ctx.status(200);	
	}
	
	public static void editRequestByRequestID(Context ctx) {
		Map<String,String> credentials = ctx.bodyAsClass(Map.class);
		Request request = new Request();

		request.setRequestID(Integer.parseInt(credentials.get("requestID")));
		request.setSubmitterId(Integer.parseInt(credentials.get("submit	terID")));
		request.setEventTypeId(new EventType(Integer.parseInt(credentials.get("eventTypeID")),""));
		request.setStatusId(new Status(Integer.parseInt(credentials.get("statusID")),""));
		request.setEventDate(LocalDate.parse(credentials.get("eventDate")).toString());
		request.setCost(Double.parseDouble(credentials.get("costs")));
		request.setDescription(credentials.get("description"));
		request.setLocation(credentials.get("location"));
		request.setSubmittedAt(credentials.get("submittedAt"));
		//try {
			ctx.status(HttpCode.CREATED);
			requestServiceImpl.editRequestByRequestID(request);
			ctx.status(200);			
			//Not sure what to return but its fine.
		//} catch (RequestSubmittedUnsuccessfully e) {
		//	//Need to write some homemade exception
		//	ctx.status(401);
		//	ctx.result(e.getMessage());
		//} 
	}
	
	public static void getRequestsByManagerID(Context ctx) {
		Map<String,String> credentials = ctx.bodyAsClass(Map.class);
		
		int managerID = Integer.parseInt(credentials.get("managerID"));
		Set<Request> requests = requestServiceImpl.getRequestsByManagerID(managerID);
		ctx.status(200);	

	}
	public static void editRequestByManagerID(Context ctx) {
		Map<String,String> credentials = ctx.bodyAsClass(Map.class);
		Request request = new Request();

		request.setRequestID(Integer.parseInt(credentials.get("requestID")));
		request.setSubmitterId(Integer.parseInt(credentials.get("submitterID")));
		request.setEventTypeId(new EventType(Integer.parseInt(credentials.get("eventTypeID")),""));
		request.setStatusId(new Status(Integer.parseInt(credentials.get("statusID")),""));
		request.setEventDate(LocalDate.parse(credentials.get("eventDate")).toString());
		request.setCost(Double.parseDouble(credentials.get("costs")));
		request.setDescription(credentials.get("description"));
		request.setLocation(credentials.get("location"));
		request.setSubmittedAt(credentials.get("submittedAt"));
		//try {
			ctx.status(HttpCode.CREATED);
			requestServiceImpl.editRequestByRequestID(request);
			ctx.status(200);	
	}
	public static void getRequestsByDeptID(Context ctx) {
		Map<String,String> credentials = ctx.bodyAsClass(Map.class);
		
		int deptHeadID = Integer.parseInt(credentials.get("deptHeadID"));
		Set<Request> requests = requestServiceImpl.getRequestsByManagerID(deptHeadID);
		ctx.status(200);	

	}
	public static void editRequestByDeptID(Context ctx) {
		Map<String,String> credentials = ctx.bodyAsClass(Map.class);
		Request request = new Request();

		request.setRequestID(Integer.parseInt(credentials.get("requestID")));
		request.setSubmitterId(Integer.parseInt(credentials.get("submitterID")));
		request.setEventTypeId(new EventType(Integer.parseInt(credentials.get("eventTypeID")),""));
		request.setStatusId(new Status(Integer.parseInt(credentials.get("statusID")),""));
		request.setEventDate(LocalDate.parse(credentials.get("eventDate")).toString());
		request.setCost(Double.parseDouble(credentials.get("costs")));
		request.setDescription(credentials.get("description"));
		request.setLocation(credentials.get("location"));
		request.setSubmittedAt(credentials.get("submittedAt"));
		//try {
			ctx.status(HttpCode.CREATED);
			requestServiceImpl.editRequestByRequestID(request);
			ctx.status(200);	
	}
}
