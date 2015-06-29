package ecar.webservices.exc;

public class SegurancaWSException extends Exception {

	private static final long serialVersionUID = 8136765247157957568L;

	public SegurancaWSException(String msg) {
		super(msg);
	}
	
	public SegurancaWSException(String msg, Throwable e) {
		super(msg,e);
	}
}
