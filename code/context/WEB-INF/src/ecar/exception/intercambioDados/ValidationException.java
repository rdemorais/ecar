package ecar.exception.intercambioDados;

import ecar.pojo.intercambioDados.MotivoRejeicaoMtr;

public class ValidationException extends Exception{

	private MotivoRejeicaoMtr motivoRejeicaoMtr;
	protected Throwable causaRaiz = null;
	private String messageKey = null;
	private String[] messageArgs = null;
	private Long numeroLinha;
	
	public ValidationException(Throwable causa, String[] messageArgsLoc,MotivoRejeicaoMtr motivoRejeicaoMtr,Long numeroLinha) {
		super();
		this.causaRaiz = causa;
		this.messageArgs = messageArgsLoc;
		this.motivoRejeicaoMtr = motivoRejeicaoMtr;
		this.numeroLinha = numeroLinha;
	}
	
	
	public String getMensagem(){
		
		String ret = motivoRejeicaoMtr.getMensagem();
    	
    	if (messageArgs != null) {
        	for (int i=0;i<messageArgs.length;i++){
        		ret = ret.replace("{"+i+"}", messageArgs[i]);
        	}
    	}            
    
    	return ret;
	}


	public MotivoRejeicaoMtr getMotivoRejeicaoMtr() {
		return motivoRejeicaoMtr;
	}


	public void setMotivoRejeicaoMtr(MotivoRejeicaoMtr motivoRejeicaoMtr) {
		this.motivoRejeicaoMtr = motivoRejeicaoMtr;
	}


	public Long getNumeroLinha() {
		return numeroLinha;
	}


	public void setNumeroLinha(Long numeroLinha) {
		this.numeroLinha = numeroLinha;
	}
}
