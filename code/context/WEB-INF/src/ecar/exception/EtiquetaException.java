package ecar.exception;

public class EtiquetaException extends Exception{

	private static final long serialVersionUID = 6000222389492360935L;
	
	public EtiquetaException (){
		
	}
	
	public EtiquetaException(String mensagem){
		this.mensagem = mensagem;
	}
	
	private String mensagem;

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	
	
}
