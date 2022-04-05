package exception;

public class IncorrectCredentialExcception extends Exception{
	private static final long serialVersionUID = -6717465192853235653L;

	public IncorrectCredentialExcception() {
		super("Incorrect Username or Password");
	}

}
