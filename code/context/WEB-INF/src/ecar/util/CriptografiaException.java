package ecar.util;

public class CriptografiaException  extends RuntimeException{

	private static final long serialVersionUID = -4238779419967538407L;

	private String codigo;

	public CriptografiaException() {
		super();
	}

	public CriptografiaException(String message) {
		super(message);
		this.codigo = message;
	}

	public CriptografiaException(String codigo, String message) {
		super(message);
		this.codigo = codigo;
	}

	public CriptografiaException(String codigo, String message, Throwable cause) {
		super(message, cause);
	}

	public CriptografiaException(String message, Throwable cause) {
		super(message, cause);
	}

	public CriptografiaException(Throwable cause) {
		super(cause);
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}



