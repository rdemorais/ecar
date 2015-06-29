package ecar.pojo;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import comum.util.ConstantesECAR;

import ecar.action.ActionSisAtributo;
import ecar.exception.ECARException;
import ecar.taglib.util.Input;

/**
 *
 * @author 70744416353
 */
public abstract class FuncaoSisAtributo {

	
	private Date dataUltManutencao;
	private String informacao;	
	private ecar.pojo.UsuarioUsu usuarioUsu;
	private Set<TipoValor> tiposValores;
	private ecar.pojo.SisAtributoSatb sisAtributoSatb;
	private FuncaoFun funcao;

	FuncaoSisAtributo (){
	}
	
	FuncaoSisAtributo (String informacao, Date dataUltManutencao,SisAtributoSatb sisAtributoSatb){
		this.dataUltManutencao = dataUltManutencao;
		this.informacao = informacao;
		this.sisAtributoSatb = sisAtributoSatb; 
	}
	
        /**
         *
         * @param sisAtributo
         * @param sequenciador
         * @param action
         * @param funcao
         * @throws ECARException
         */
        public abstract void atualizaListaTiposValores(SisAtributoSatb sisAtributo,SequenciadoraSeq sequenciador,ActionSisAtributo action,FuncaoFun funcao) throws ECARException;
	
        /**
         *
         * @param tiposValores
         * @param funcao
         */
        public abstract void copiarTipoValorSemID(Set<TipoValor> tiposValores,FuncaoFun funcao);
	
        /**
         *
         * @return
         */
        public FuncaoFun getFuncao() {
		return funcao;
	}

        /**
         *
         * @param funcao
         */
        public void setFuncao(FuncaoFun funcao) {
		this.funcao = funcao;
	}

	
        /**
         *
         * @return
         */
        public ecar.pojo.UsuarioUsu getUsuarioUsu() {
		return usuarioUsu;
	}

        /**
         *
         * @param usuarioUsu
         */
        public void setUsuarioUsu(ecar.pojo.UsuarioUsu usuarioUsu) {
		this.usuarioUsu = usuarioUsu;
	}

        /**
         *
         * @return
         */
        public Set<TipoValor> getTiposValores() {
		return tiposValores;
	}

        /**
         *
         * @param tiposValores
         */
        public void setTiposValores(Set<TipoValor> tiposValores) {
		this.tiposValores = tiposValores;
	}

        /**
         *
         * @return
         */
        public ecar.pojo.SisAtributoSatb getSisAtributoSatb() {
		return sisAtributoSatb;
	}

        /**
         *
         * @param sisAtributoSatb
         */
        public void setSisAtributoSatb(ecar.pojo.SisAtributoSatb sisAtributoSatb) {
		this.sisAtributoSatb = sisAtributoSatb;
	}

        /**
         *
         * @return
         */
        public Date getDataUltManutencao() {
		return dataUltManutencao;
	}
	
        /**
         *
         * @param dataUltManutencao
         */
        public void setDataUltManutencao(Date dataUltManutencao) {
		this.dataUltManutencao = dataUltManutencao;
	}


        /**
         *
         * @return
         */
        public String getInformacao() {
		return informacao;
	}

        /**
         *
         * @param informacao
         */
        public void setInformacao(String informacao) {
		this.informacao = informacao;
	}
	
	
	
	/**
	 * Retorna o Objeto do tipo Mascara contido na lista.
	 * @return
	 */
	public TipoValor obterTipoMascara() {
		TipoValor tipoSeq = null;
		
		Iterator<TipoValor> it = getTiposValores().iterator();
		
		while (it.hasNext()) {
			tipoSeq = (TipoValor)it.next();
			
			if (tipoSeq.getTipo().compareTo(TipoValorEnum.MASCARA) == 0){
				break;
			}
		}
		
		return tipoSeq;
	}

	/**
	 * Formata a informação de acordo com o tipo de grupo de atributo associado. 
	 * @return
	 */
	public String getInformacaoFormatada(){
		
		Long codTipoExibicao = getSisAtributoSatb().getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().getCodSteg();
		String retorno;
		
		switch (codTipoExibicao.intValue()) {
		case Input.IMAGEM:
			retorno = obterNomeArquivo(getInformacao());
			break;
		default:
			retorno = getInformacao();
			break;
		}
		
		return retorno;
		
	}

	/**
	 * Obtem o nome do arquivo retirando o caminho absoluto.
	 * @param caminhoArquivo
	 * @return
	 */
	private String obterNomeArquivo(String caminhoArquivo) {
		
		String nomeArquivo = "";
		
		int posicaoUltimoCaracterSeparadorArquivo = caminhoArquivo.lastIndexOf(ConstantesECAR.CARACTER_DELIMITADOR_PASTA);
		
		nomeArquivo = caminhoArquivo.substring(posicaoUltimoCaracterSeparadorArquivo+1,caminhoArquivo.length());
		
		return nomeArquivo;
	}

}
