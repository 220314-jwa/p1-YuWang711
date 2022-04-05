package exception;

public class UsernameAlreadyExistsException extends Exception{

	private static final long serialVersionUID = -8468708953690530041L;

	public UsernameAlreadyExistsException() {
		super("User already exist in database");
	}

}
