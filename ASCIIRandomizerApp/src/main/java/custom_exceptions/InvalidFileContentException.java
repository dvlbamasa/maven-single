package custom_exceptions;

public class InvalidFileContentException extends Exception {
	
	public InvalidFileContentException(String exception) {
		super(exception);
	}
}