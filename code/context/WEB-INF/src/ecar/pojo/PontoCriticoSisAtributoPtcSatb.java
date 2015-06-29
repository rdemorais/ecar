package ecar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import comum.util.Util;

import ecar.action.ActionSisAtributo;
import ecar.exception.ECARException;

/**
 *
 * @author 70744416353
 */
public class PontoCriticoSisAtributoPtcSatb extends FuncaoSisAtributo implements Serializable, PaiFilho {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1026211134617471065L;
	
	private ecar.pojo.PontoCriticoSisAtributoPtcSatbPK comp_id;
	
	private ecar.pojo.PontoCriticoPtc pontoCriticoPtc;
	
	/**
	 * Default Constructor
	 */
	public PontoCriticoSisAtributoPtcSatb() {
		super();
	}

	/**
	 * Min Constructor
	 * @param comp_id
	 */
	public PontoCriticoSisAtributoPtcSatb(PontoCriticoSisAtributoPtcSatbPK comp_id) {
		this.comp_id = comp_id;
	}

	/**
	 * Full Constructor
	 * @param comp_id
         * @param pontoCriticoPtc
         * @param sisAtributoSatb
         * @param informacaoIettSatb
	 * @param dataUltManutencaoIettSatb
	 */
	public PontoCriticoSisAtributoPtcSatb(PontoCriticoSisAtributoPtcSatbPK comp_id, PontoCriticoPtc pontoCriticoPtc, SisAtributoSatb sisAtributoSatb, String informacaoIettSatb, Date dataUltManutencaoIettSatb) {
		super (informacaoIettSatb,dataUltManutencaoIettSatb,sisAtributoSatb);
		this.comp_id = comp_id;
		this.pontoCriticoPtc = pontoCriticoPtc;
	}


        /**
         *
         * @return
         */
        public ecar.pojo.PontoCriticoSisAtributoPtcSatbPK getComp_id() {
		return comp_id;
	}

        /**
         *
         * @param comp_id
         */
        public void setComp_id(ecar.pojo.PontoCriticoSisAtributoPtcSatbPK comp_id) {
		this.comp_id = comp_id;
	}

        /**
         *
         * @return
         */
        public ecar.pojo.PontoCriticoPtc getPontoCriticoPtc() {
		return pontoCriticoPtc;
	}

        /**
         *
         * @param pontoCriticoPtc
         */
        public void setPontoCriticoPtc(ecar.pojo.PontoCriticoPtc pontoCriticoPtc) {
		this.pontoCriticoPtc = pontoCriticoPtc;
	}


        /**
         *
         */
        public void atribuirPKPai(){
		comp_id = new PontoCriticoSisAtributoPtcSatbPK();
		comp_id.setCodPtc(this.getPontoCriticoPtc().getCodPtc());
		comp_id.setCodSatb(this.getSisAtributoSatb().getCodSatb());
	}
	
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codPtc", getComp_id().getCodPtc())
            .append("codSatb", getComp_id().getCodSatb())
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof PontoCriticoSisAtributoPtcSatb) ) return false;
        PontoCriticoSisAtributoPtcSatb castOther = (PontoCriticoSisAtributoPtcSatb) other;
        return new EqualsBuilder()
            .append(this.getPontoCriticoPtc().getCodPtc(), castOther.getPontoCriticoPtc().getCodPtc())
            .append(this.getSisAtributoSatb().getCodSatb(), castOther.getSisAtributoSatb().getCodSatb())
            .isEquals();
    }

	@Override
	public void atualizaListaTiposValores(SisAtributoSatb sisAtributo, SequenciadoraSeq sequenciador, ActionSisAtributo action, FuncaoFun funcao)
			throws ECARException {
		
		
		Set tiposValores = new HashSet();
		
		//Tipo Valor Mês
		TipoValor tipoMes = new TipoValor();
		String mes = action.obterMes(this.getInformacao(),sisAtributo.getMascara());
		tipoMes.setConteudo(mes);
		tipoMes.setPontoCriticoSisAtributo(this);
		tipoMes.setTipo(TipoValorEnum.MES);
		tipoMes.setFuncao(funcao);
		tiposValores.add(tipoMes);
		
		//Tipo Valor Ano
		TipoValor tipoAno = new TipoValor();
		String ano = action.obterAno(this.getInformacao(),sisAtributo.getMascara());
		tipoAno.setConteudo(ano);
		tipoAno.setPontoCriticoSisAtributo(this);
		tipoAno.setTipo(TipoValorEnum.ANO);
		tipoAno.setFuncao(funcao);
		tiposValores.add(tipoAno);
		
		//Tipo Valor Sequencial
		TipoValor tipoSequencial = new TipoValor();
		if (sequenciador != null) {//Se o sequenciador existe então utilizo o sequenciador gerado (caso de inclusão de item)
			tipoSequencial.setConteudo(sequenciador.getSequenciaSeq()+"");
		} else {//Se o sequenciador não existe então obtem o sequencial do proprio objeto atributoLivreBean e utiliza na lista (caso de alteração de item)
			/*IMPORTANTE:
			 * O objeto atributoLivreBean do tipo PontoCriticoSisAtributoPtcSatb é um objeto criado com os dados da tela portanto a lista de
			 * tipos Valores está nula. Porém o pontoCritico contido no atributoLivreBean é proveniente do banco e possui a lista completa de
			 * dos AtributosLivres armazenados.     
			*/
			
			PontoCriticoSisAtributoPtcSatb pontoCriticoSisAtributoNegocio = this.getPontoCriticoPtc().buscarItemEstruturaSisAtributoLista(sisAtributo);
			
			TipoValor seq = null;
			Iterator<TipoValor> it = null;
			
			it = pontoCriticoSisAtributoNegocio.getTiposValores().iterator();

			while (it.hasNext()) {
				seq = (TipoValor)it.next();
				
				if (seq.getTipo().compareTo(TipoValorEnum.SEQUENCIAL) == 0){
					break;
				}
			}
			
			tipoSequencial.setConteudo(seq.getConteudo());
		}
		tipoSequencial.setPontoCriticoSisAtributo(this);
		tipoSequencial.setTipo(TipoValorEnum.SEQUENCIAL);
		tipoSequencial.setFuncao(funcao);
		tiposValores.add(tipoSequencial);
		
		//Tipo Valor Mascara
		if (!sisAtributo.isAtributoAutoIcremental()) { 		
			TipoValor tipoMascara = new TipoValor();
			tipoMascara.setConteudo(sisAtributo.getMascara());
			tipoMascara.setPontoCriticoSisAtributo(this);
			tipoMascara.setTipo(TipoValorEnum.MASCARA);
			tipoMascara.setFuncao(funcao);
			tiposValores.add(tipoMascara);
		}
		
		this.setTiposValores(tiposValores);
		
		
	}
	
	
	/**
	 * Copia a lista de tipos Valores sem os ID's
	 * @param tiposValores
	 * @param funcao
	 * @Override
	 */
	public void copiarTipoValorSemID(Set<TipoValor> tiposValores,FuncaoFun funcao) {
		
		Set listaTiposValores = new HashSet();
		for (TipoValor tipoValor : tiposValores) {
			
			TipoValor tipoValorInner = new TipoValor();
			
			tipoValorInner.setConteudo(tipoValor.getConteudo());
			tipoValorInner.setPontoCriticoSisAtributo(this);
			tipoValorInner.setTipo(tipoValor.getTipo());
			tipoValorInner.setFuncao(funcao);
			
			listaTiposValores.add(tipoValorInner);
			
		}
		this.setTiposValores(listaTiposValores);
		
	}

	/**
	 * Só utilizado em atributos do tipo ID, baseado na mascara do atributo livre gera a lista de tipos valores.
	 * Para atributos do tipo auto incremental copia o valor da informação para o conteudo do tipo valor.  
	 * 
	 * @throws ECARException
	 */
	public void gerarTiposValores() throws ECARException {
		
		ActionSisAtributo action = new ActionSisAtributo();
		Set<TipoValor> tiposValores = new HashSet<TipoValor>();
		
		if (getTiposValores() == null || getTiposValores().isEmpty()){
			if (getSisAtributoSatb().isAtributoContemMascara()){
				//Tipo Valor Mês
				TipoValor tipoMes = new TipoValor();
				String mes = action.obterMes(this.getInformacao(),getSisAtributoSatb().getMascara());
				tipoMes.setConteudo(mes);
				tipoMes.setPontoCriticoSisAtributo(this);
				tipoMes.setTipo(TipoValorEnum.MES);
				tipoMes.setFuncao(getFuncao());
				tiposValores.add(tipoMes);
				
				//Tipo Valor Ano
				TipoValor tipoAno = new TipoValor();
				String ano = action.obterAno(this.getInformacao(),getSisAtributoSatb().getMascara());
				tipoAno.setConteudo(ano);
				tipoAno.setPontoCriticoSisAtributo(this);
				tipoAno.setTipo(TipoValorEnum.ANO);
				tipoAno.setFuncao(getFuncao());
				tiposValores.add(tipoAno);
				
				//Sequencial
				TipoValor tipoSequencial = new TipoValor();
				String sequencial = Util.removerZerosEsquerda(action.obterSequencial(this.getInformacao(),getSisAtributoSatb().getMascara()));
				tipoSequencial.setConteudo(sequencial);
				tipoSequencial.setPontoCriticoSisAtributo(this);
				tipoSequencial.setTipo(TipoValorEnum.SEQUENCIAL);
				tipoSequencial.setFuncao(getFuncao());
				tiposValores.add(tipoSequencial);

				//Mascara
				TipoValor tipoMascara = new TipoValor();
				tipoMascara.setConteudo(getSisAtributoSatb().getMascara());
				tipoMascara.setPontoCriticoSisAtributo(this);
				tipoMascara.setTipo(TipoValorEnum.MASCARA);
				tipoMascara.setFuncao(getFuncao());
				tiposValores.add(tipoMascara);
				
			} else if (getSisAtributoSatb().isAtributoAutoIcremental()){
				
				//Tipo Valor Mês
				TipoValor tipoMes = new TipoValor();
				String mes = action.obterMes(this.getInformacao(),getSisAtributoSatb().getMascara());
				tipoMes.setConteudo(mes);
				tipoMes.setPontoCriticoSisAtributo(this);
				tipoMes.setTipo(TipoValorEnum.MES);
				tipoMes.setFuncao(getFuncao());
				tiposValores.add(tipoMes);
				
				//Tipo Valor Ano
				TipoValor tipoAno = new TipoValor();
				String ano = action.obterAno(this.getInformacao(),getSisAtributoSatb().getMascara());
				tipoAno.setConteudo(ano);
				tipoAno.setPontoCriticoSisAtributo(this);
				tipoAno.setTipo(TipoValorEnum.ANO);
				tipoAno.setFuncao(getFuncao());
				tiposValores.add(tipoAno);
				
				//Sequencial
				TipoValor tipoSequencial = new TipoValor();
				tipoSequencial.setConteudo(Util.removerZerosEsquerda(this.getInformacao()));
				tipoSequencial.setPontoCriticoSisAtributo(this);
				tipoSequencial.setTipo(TipoValorEnum.SEQUENCIAL);
				tipoSequencial.setFuncao(getFuncao());
				tiposValores.add(tipoSequencial);

			}
			
			setTiposValores(tiposValores);
		}
		
	}

}
