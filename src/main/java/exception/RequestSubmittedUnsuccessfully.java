package exception;

public class RequestSubmittedUnsuccessfully extends Exception {
	private static final long serialVersionUID = 0;

	public RequestSubmittedUnsuccessfully() {
		super("Unable to submite request");
	}
}
