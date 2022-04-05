package main;

import java.util.Map;

import exception.RequestSubmittedUnsuccessfully;
import exception.UsernameAlreadyExistsException;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import service.RequestServiceImpl;

public class RequestController {
	private static RequestServiceImpl requestServiceImpl = new RequestServiceImpl();
	
	public static void submitRequest(Context ctx) {
		Map<String,String> credentials = ctx.bodyAsClass(Map.class);
		String firstName = credentials.get("register-first-name");
		String lastName = credentials.get("register-last-name");
		String username = credentials.get("register-username");
		String password = credentials.get("register-password");
/*
  	private int employee_id;
	private String first_name;
	private String last_name;
	private String username;
	private int manager_id;
	private int dept_id;
	private String password;
	private String salt;
	protected int test;
 * 
 */
		Request request = new Request();
		ctx.status(200);
		try {
			ctx.status(HttpCode.CREATED);
			requestServiceImpl.submitRequest(request);
			//Not sure what to return but its fine.
		} catch (RequestSubmittedUnsuccessfully e) {
			//Need to write some homemade exception
			ctx.status(401);
			ctx.result(e.getMessage());
		} 
	}
	
	public static void getRequestsByEmployeeID(Context ctx){
		Map<String,String> credentials = ctx.bodyAsClass(Map.class);

	}
	
	public static void editRequestByRequestID(Context ctx) {
		Map<String,String> credentials = ctx.bodyAsClass(Map.class);

	}
	
	public static void getRequestsByManagerID(Context ctx) {
		Map<String,String> credentials = ctx.bodyAsClass(Map.class);

	}
	public static void editRequestByManagerID(Context ctx) {
		Map<String,String> credentials = ctx.bodyAsClass(Map.class);

	}
	public static void getRequestsByDeptID(Context ctx) {
		Map<String,String> credentials = ctx.bodyAsClass(Map.class);

	}
	public static void editRequestByDeptID(Context ctx) {
		Map<String,String> credentials = ctx.bodyAsClass(Map.class);

	}
}
