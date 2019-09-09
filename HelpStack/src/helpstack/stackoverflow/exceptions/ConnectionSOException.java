package helpstack.stackoverflow.exceptions;

public class ConnectionSOException extends Exception{

	private static final long serialVersionUID = 1L;

	public ConnectionSOException() {
		
	}
	
	@Override
	public String getMessage() {
		return "Lost connection to StackOverflow";
	}

}
