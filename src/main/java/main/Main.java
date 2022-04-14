package main;
import io.javalin.Javalin;

import io.javalin.http.HttpCode;
import io.javalin.http.staticfiles.Location;
import static io.javalin.apibuilder.ApiBuilder.*;

import database.postgre.*;

public class Main {
	
	final static String getRequests = "/getRequests";
	final static String editRequest = "/editRequest";
	
	public static void main(String[] args) {
		//Main handles request https requests
	    //Possibly need a set up function for sql
	    //Setup block
	    //psql directory: C:\Users\timmy\scoop\apps\postgresql\14.2\data
	    //pg_ctl -D "C:\Users\timmy\scoop\apps\postgresql\14.2\data" start
	    //pg_ctl -D "C:\Users\timmy\scoop\apps\postgresql\14.2\data" stop
	    //pg_ctl -D "C:\Users\timmy\scoop\apps\postgresql\14.2\data" restart
		Javalin app = Javalin.create(config->{
				config.enableCorsForAllOrigins();
				config.addStaticFiles("./html", Location.CLASSPATH);
		}).start(8080);
		
		
		
		
		
		app.routes(()->{
			path("/users", ()->{
				path("/register",()->{
					post(UsersController::register);
				});
				path("{id}", () -> {
					get(UsersController::getUserById);
				});
				path("/auth", ()->{
					post(UsersController::login);
				});
				path("/getDepartments", ()->{
					post(UsersController::getDepartments);
				});
			});
	 		path("/requests", ()->{
				path("/getEventTypes", ()->{
					post(RequestController::getAllEventType);
				});
				path("/employee", ()->{
					path(getRequests, ()->{
						post(RequestController::getRequestsByEmployeeID);
					});
					path("/submitRequest", ()->{
						post(RequestController::submitRequest);
					});
					path(editRequest, ()->{
						post(RequestController::editRequestByRequestID);
					});
				});
				path("/manager", ()->{
					path(getRequests, ()->{
						post(RequestController::getRequestsByManagerID);
					});
					path(editRequest, ()->{
						post(RequestController::editRequestByManagerID);
					});
				});
				path("/deptHead", ()->{
					path(getRequests, ()->{
						post(RequestController::getRequestsByDeptID);
					});
					path(editRequest, ()->{
						post(RequestController::editRequestByDeptID);
					});
				});
			});
		});
	}

}
