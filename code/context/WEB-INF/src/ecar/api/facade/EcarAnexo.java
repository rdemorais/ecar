package ecar.api.facade;

import ecar.pojo.AcompRelatorioArel;
import ecar.pojo.ItemEstrutUploadIettup;

/**
 * Wrapper class para a classe ItemEstrutUploadIettup que representa
 * os arquivos anexos dos itens de estrutura.
 * 
 * Podem ser obtidos por:
 * <code>{@link ItemEstrutura#getAnexos()}</code>
 * 
 * 
 * @author 82035644020
 *
 */
public class EcarAnexo implements EcarWrapperInterface<ItemEstrutUploadIettup> {

	private ItemEstrutUploadIettup anexo = null; 
	private ItemEstrutura item = null;
	
	
	public EcarAnexo(ItemEstrutUploadIettup anexo){
		this.anexo = anexo;
	}
	
	public EcarAnexo(ItemEstrutUploadIettup anexo, ItemEstrutura item){
		this.anexo = anexo;
		this.item = item;
	}
	
	
	public long getId(){
		return anexo.getCodIettup();
	}
	
	/**
	 * Retorna o nome original do arquivo, ou seja o nome do 
	 * arquivo no momento em que foi feito o upload.
	 * 
	 * @return nome original do arquivo
	 * 
	 */
	public String getNomeOriginal(){
		return this.anexo.getNomeOriginalIettup();
	}
	
	/**
	 * Retorna o nome interno utilizado pelo ecar para identificar um arquivo anexo.
	 * 
	 * @return
	 */
	public String getNomeInternoEcar(){
		return this.anexo.getArquivoIettup();
	}
	
	/**
	 * Retorna a descrição do arquivo.
	 * @return
	 */
	public String getDescricao(){
		return this.anexo.getDescricaoIettup();
	}
	
	/**
	 * Retorna o item de estrutura que possui o anexo.
	 * @return
	 */
	public ItemEstrutura getItemEstrutura(){
		
		if(item != null) return item;
		
		return new ItemEstrutura(anexo.getItemEstruturaIett());
	}
	
	public EcarData getDataInclusao(){
		return new EcarData(this.anexo.getDataInclusaoIettup());
	}

	
	public String toString(){
		return this.getNomeOriginal() + "\t" + this.getNomeInternoEcar() +
			   "\t" + this.getDescricao() + "\t" + this.getDataInclusao().getDataFormatada();	
		
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see ecar.api.facade.EcarWrapperInterface#getRealObject()
	 */
	public ItemEstrutUploadIettup getRealObject() {
		return this.anexo;
	}

	
	public EcarData getDataUltimaManutencao(){
		
		if(this.getRealObject().getUsuarioUsuManutencao() == null || 
				this.getRealObject().getUsuarioUsuManutencao().getDataUltAlteracaoUsu() == null){
			return null;
		}else{
			return new EcarData(this.getRealObject().getUsuarioUsuManutencao().getDataUltAlteracaoUsu());
		}
	}
	
	public String getNomeCategoriaAnexo(){
		return this.getRealObject().getItemEstrUplCategIettuc().getNomeIettuc();
	}
	
	public String getDescricaoCategoriaAnexo(){
		return this.getRealObject().getItemEstrUplCategIettuc().getDescricaoIettuc();
	}

	public String getNomeUsuarioUltimaManutencao(){
		String nome = getRealObject().getUsuarioUsuManutencao().getNomeUsu();
		if(nome == null){
			return "";
		}else{
			return nome;
		}
	}
	
	/**
	 * 
	 * 
	 * @return
	 * @TODO: criar wrapper para AcompRelatorioArel (Parecer)
	 */
	public AcompRelatorioArel getRelatorioAcompanhamento(){
		return this.getRealObject().getAcompRelatorioArel(); 
	}
	
	/**
	 * Retorna a descrição do anexo
	 * @return
	 */
	public String getDescricaoAnexo(){
		return this.getRealObject().getDescricaoIettup();
	}
	
	
	public String getResponsavelInclusao(){
		if(this.getRealObject().getUsuarioUsu() != null){
			return this.getRealObject().getUsuarioUsu().getNomeUsu();
		}
		
		return "";
	}

	
	public String getTipoAnexo(){
		if(this.getRealObject().getUploadTipoArquivoUta() != null){
			return this.getRealObject().getUploadTipoArquivoUta().getDescricaoUta();
		}
		
		return "";
	}
	
	
	public String getTipoCategoria(){
		
		if(this.getRealObject().getItemEstrUplCategIettuc() != null &&
				this.getRealObject().getItemEstrUplCategIettuc().getUploadTipoCategoriaUtc() != null) { 
			return this.getRealObject().getItemEstrUplCategIettuc().getUploadTipoCategoriaUtc().getNomeUtc();
		}
		
		return "";
	}
	
	public String getTamanhoAnexo(){
		long t = this.getRealObject().getTamanhoIettup();
		return Long.toString(t);
	}
	
}
