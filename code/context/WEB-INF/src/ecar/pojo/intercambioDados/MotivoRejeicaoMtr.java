package ecar.pojo.intercambioDados;

public class MotivoRejeicaoMtr {

	private Long id;
	private String mensagem;

	/**
	 * 
	 */
	public MotivoRejeicaoMtr(){
		
	}
	
	public MotivoRejeicaoMtr(Long id,String mensagem) {
		this.id = id;
		this.mensagem = mensagem;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	
}
