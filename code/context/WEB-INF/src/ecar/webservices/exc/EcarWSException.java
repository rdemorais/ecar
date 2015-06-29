package ecar.webservices.exc;

public class EcarWSException extends Exception{

	private static final long serialVersionUID = 1L;

	public EcarWSException(ExceptionGlossario e) {
		super("["+e.getCod()+"] "+e.getMsg());
	}
	
	public EcarWSException(String msg) {
		super(msg);
	}
	
	public EcarWSException() {
		super();
	}
}
