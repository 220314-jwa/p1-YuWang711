package exception;

public class UserNotExistException extends Exception{

	private static final long serialVersionUID = 6613568113915530487L;

	public UserNotExistException() {
		super("User does not exist");
	}

}
