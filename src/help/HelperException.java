package help;
@SuppressWarnings("serial")
public class HelperException extends Exception{
	public HelperException(String exception){
		super(exception);
	}
	public HelperException(Throwable exception) {
		super(exception);
	}
}