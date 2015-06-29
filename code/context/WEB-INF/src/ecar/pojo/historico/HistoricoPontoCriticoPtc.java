package ecar.pojo.historico;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;

import ecar.pojo.AcompRelatorioArel;
import ecar.pojo.ApontamentoApt;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.PontoCriticoPtc;
import ecar.pojo.PontoCriticoSisAtributoPtcSatb;
import ecar.pojo.PontocriticoCorPtccor;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;
import ecar.pojo.UsuarioUsu;

/**
 *
 * @author 70744416353
 */
public class HistoricoPontoCriticoPtc implements Serializable, IHistorico {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1761805881865401430L;

	private Date dataHistorico;
	private String historico;
	private Long idHistorico;
	private Long idObjetoSerializado;
	private Long tipoHistorico;
	private UsuarioUsu usuarioUsuOperacao;

	/** identifier field */
	private Long codPtc;

	/** nullable persistent field */
	private String indAtivoPtc;

	/** nullable persistent field */
	private Date dataInclusaoPtc;

	/** nullable persistent field */
	private Date dataSolucaoPtc;

	/** nullable persistent field */
	private Date dataUltManutencaoPtc;

	/** nullable persistent field */
	private String descricaoSolucaoPtc;

	/** nullable persistent field */
	private String indAmbitoInternoGovPtc;

	/** nullable persistent field */
	private Date dataLimitePtc;

	/** nullable persistent field */
	private Date dataIdentificacaoPtc;

	/** nullable persistent field */
	private String descricaoPtc;

	/** persistent field */
	private ItemEstruturaIett itemEstruturaIett;

	/** persistent field */
	private UsuarioUsu usuarioUsu;

	/** persistent field */
	private UsuarioUsu usuarioUsuInclusao;

	/** persistent field */
	private UsuarioUsu usuarioUsuByCodUsuUltManutPtc;

	private SisAtributoSatb sisAtributoTipo;
	


	/** persistent field */
	private AcompRelatorioArel acompRelatorioArel;

	/** persistent field */
	private String indExcluidoPtc;
	
	/** persistent field */
	private Set<PontoCriticoSisAtributoPtcSatb> pontoCriticoSisAtributoPtcSatbs;
	
	/** persistent field */
	private Set<PontocriticoCorPtccor> pontoCriticoCorPtccores;

	/** persistent field */
	private Set<ApontamentoApt> apontamentoApts;
	
	private SisGrupoAtributoSga sisGrupoAtributoSga;
	
	private List atributoEstrutura;
	
        /**
         *
         * @return
         */
        public Date getDataHistorico() {
		return dataHistorico;
	}

        /**
         *
         * @param dataHistorico
         */
        public void setDataHistorico(Date dataHistorico) {
		this.dataHistorico = dataHistorico;
	}

        /**
         *
         * @return
         */
        public String getHistorico() {
		return historico;
	}

        /**
         *
         * @param historico
         */
        public void setHistorico(String historico) {
		this.historico = historico;
	}

        /**
         *
         * @return
         */
        public Long getIdHistorico() {
		return idHistorico;
	}

        /**
         *
         * @param idHistorico
         */
        public void setIdHistorico(Long idHistorico) {
		this.idHistorico = idHistorico;
	}

        /**
         *
         * @return
         */
        public Long getIdObjetoSerializado() {
		return idObjetoSerializado;
	}

        /**
         *
         * @param idObjetoSerializado
         */
        public void setIdObjetoSerializado(Long idObjetoSerializado) {
		this.idObjetoSerializado = idObjetoSerializado;
	}

        /**
         *
         * @return
         */
        public Long getTipoHistorico() {
		return tipoHistorico;
	}

        /**
         *
         * @param tipoHistorico
         */
        public void setTipoHistorico(Long tipoHistorico) {
		this.tipoHistorico = tipoHistorico;
	}

        /**
         *
         * @return
         */
        public UsuarioUsu getUsuarioUsu() {
		return usuarioUsu;
	}

        /**
         *
         * @param usuarioUsu
         */
        public void setUsuarioUsu(UsuarioUsu usuarioUsu) {
		this.usuarioUsu = usuarioUsu;
	}

        /**
         *
         * @return
         */
        public Long getCodPtc() {
		return codPtc;
	}

        /**
         *
         * @param codPtc
         */
        public void setCodPtc(Long codPtc) {
		this.codPtc = codPtc;
	}

        /**
         *
         * @return
         */
        public String getIndAtivoPtc() {
		return indAtivoPtc;
	}

        /**
         *
         * @param indAtivoPtc
         */
        public void setIndAtivoPtc(String indAtivoPtc) {
		this.indAtivoPtc = indAtivoPtc;
	}

        /**
         *
         * @return
         */
        public Date getDataInclusaoPtc() {
		return dataInclusaoPtc;
	}

        /**
         *
         * @param dataInclusaoPtc
         */
        public void setDataInclusaoPtc(Date dataInclusaoPtc) {
		this.dataInclusaoPtc = dataInclusaoPtc;
	}

        /**
         *
         * @return
         */
        public Date getDataSolucaoPtc() {
		return dataSolucaoPtc;
	}

        /**
         *
         * @param dataSolucaoPtc
         */
        public void setDataSolucaoPtc(Date dataSolucaoPtc) {
		this.dataSolucaoPtc = dataSolucaoPtc;
	}

        /**
         *
         * @return
         */
        public Date getDataUltManutencaoPtc() {
		return dataUltManutencaoPtc;
	}

        /**
         *
         * @param dataUltManutencaoPtc
         */
        public void setDataUltManutencaoPtc(Date dataUltManutencaoPtc) {
		this.dataUltManutencaoPtc = dataUltManutencaoPtc;
	}

        /**
         *
         * @return
         */
        public String getDescricaoSolucaoPtc() {
		return descricaoSolucaoPtc;
	}

        /**
         *
         * @param descricaoSolucaoPtc
         */
        public void setDescricaoSolucaoPtc(String descricaoSolucaoPtc) {
		this.descricaoSolucaoPtc = descricaoSolucaoPtc;
	}

        /**
         *
         * @return
         */
        public String getIndAmbitoInternoGovPtc() {
		return indAmbitoInternoGovPtc;
	}

        /**
         *
         * @param indAmbitoInternoGovPtc
         */
        public void setIndAmbitoInternoGovPtc(String indAmbitoInternoGovPtc) {
		this.indAmbitoInternoGovPtc = indAmbitoInternoGovPtc;
	}

        /**
         *
         * @return
         */
        public Date getDataLimitePtc() {
		return dataLimitePtc;
	}

        /**
         *
         * @param dataLimitePtc
         */
        public void setDataLimitePtc(Date dataLimitePtc) {
		this.dataLimitePtc = dataLimitePtc;
	}

        /**
         *
         * @return
         */
        public Date getDataIdentificacaoPtc() {
		return dataIdentificacaoPtc;
	}

        /**
         *
         * @param dataIdentificacaoPtc
         */
        public void setDataIdentificacaoPtc(Date dataIdentificacaoPtc) {
		this.dataIdentificacaoPtc = dataIdentificacaoPtc;
	}

        /**
         *
         * @return
         */
        public String getDescricaoPtc() {
		return descricaoPtc;
	}

        /**
         *
         * @param descricaoPtc
         */
        public void setDescricaoPtc(String descricaoPtc) {
		this.descricaoPtc = descricaoPtc;
	}

        /**
         *
         * @return
         */
        public ItemEstruturaIett getItemEstruturaIett() {
		return itemEstruturaIett;
	}

        /**
         *
         * @param itemEstruturaIett
         */
        public void setItemEstruturaIett(ItemEstruturaIett itemEstruturaIett) {
		this.itemEstruturaIett = itemEstruturaIett;
	}

        /**
         *
         * @return
         */
        public UsuarioUsu getUsuarioUsuInclusao() {
		return usuarioUsuInclusao;
	}

        /**
         *
         * @param usuarioUsuInclusao
         */
        public void setUsuarioUsuInclusao(UsuarioUsu usuarioUsuInclusao) {
		this.usuarioUsuInclusao = usuarioUsuInclusao;
	}

        /**
         *
         * @return
         */
        public UsuarioUsu getUsuarioUsuByCodUsuUltManutPtc() {
		return usuarioUsuByCodUsuUltManutPtc;
	}

        /**
         *
         * @param usuarioUsuByCodUsuUltManutPtc
         */
        public void setUsuarioUsuByCodUsuUltManutPtc(UsuarioUsu usuarioUsuByCodUsuUltManutPtc) {
		this.usuarioUsuByCodUsuUltManutPtc = usuarioUsuByCodUsuUltManutPtc;
	}

        /**
         *
         * @return
         */
        public SisAtributoSatb getSisAtributoTipo() {
		return sisAtributoTipo;
	}

        /**
         *
         * @param sisAtributoTipo
         */
        public void setSisAtributoTipo(SisAtributoSatb sisAtributoTipo) {
		this.sisAtributoTipo = sisAtributoTipo;
	}

        /**
         *
         * @return
         */
        public AcompRelatorioArel getAcompRelatorioArel() {
		return acompRelatorioArel;
	}

        /**
         *
         * @param acompRelatorioArel
         */
        public void setAcompRelatorioArel(AcompRelatorioArel acompRelatorioArel) {
		this.acompRelatorioArel = acompRelatorioArel;
	}

        /**
         *
         * @return
         */
        public String getIndExcluidoPtc() {
		return indExcluidoPtc;
	}

        /**
         *
         * @param indExcluidoPtc
         */
        public void setIndExcluidoPtc(String indExcluidoPtc) {
		this.indExcluidoPtc = indExcluidoPtc;
	}

        /**
         *
         * @param objPai
         */
        public void carregar(Object objPai) {
		
		PontoCriticoPtc pct = (PontoCriticoPtc) objPai;
		
		this.codPtc = pct.getCodPtc();
		this.idObjetoSerializado = pct.getCodPtc();
		this.indAtivoPtc = pct.getIndAtivoPtc();
		this.dataInclusaoPtc = pct.getDataInclusaoPtc();
		this.dataSolucaoPtc = pct.getDataSolucaoPtc();
		this.dataUltManutencaoPtc = pct.getDataUltManutencaoPtc();
		this.descricaoSolucaoPtc = pct.getDescricaoSolucaoPtc();
		this.indAmbitoInternoGovPtc = pct.getIndAmbitoInternoGovPtc();
		this.dataLimitePtc = pct.getDataLimitePtc();
		this.dataIdentificacaoPtc = pct.getDataIdentificacaoPtc();
		this.descricaoPtc = pct.getDescricaoPtc();
		this.usuarioUsu = pct.getUsuarioUsu();
		this.usuarioUsuInclusao = pct.getUsuarioUsuInclusao();
		this.usuarioUsuByCodUsuUltManutPtc = pct.getUsuarioUsuByCodUsuUltManutPtc();
		this.sisAtributoTipo = pct.getSisAtributoTipo();
		this.acompRelatorioArel = pct.getAcompRelatorioArel();
		this.indExcluidoPtc = pct.getIndExcluidoPtc();
		this.itemEstruturaIett = pct.getItemEstruturaIett();
		
		Hibernate.initialize(pct.getApontamentoApts());
		if (pct.getApontamentoApts() != null)
			this.apontamentoApts = pct.getApontamentoApts();
		else 
			this.apontamentoApts = new HashSet<ApontamentoApt>();

		Hibernate.initialize(pct.getPontoCriticoCorPtccores());
		if (pct.getPontoCriticoCorPtccores() != null)
			this.pontoCriticoCorPtccores = pct.getPontoCriticoCorPtccores();
		else 
			this.pontoCriticoCorPtccores = new HashSet<PontocriticoCorPtccor>();

		Hibernate.initialize(pct.getPontoCriticoSisAtributoPtcSatbs());
		if (pct.getPontoCriticoSisAtributoPtcSatbs() != null)
			this.pontoCriticoSisAtributoPtcSatbs = pct.getPontoCriticoSisAtributoPtcSatbs();
		else
			this.pontoCriticoSisAtributoPtcSatbs = new HashSet<PontoCriticoSisAtributoPtcSatb>();
		
	}

        /**
         *
         * @return
         */
        public Object descarregar() {
		try {
			PontoCriticoPtc clone = (PontoCriticoPtc) super.clone();

//			clone.setCodPtc(codPtc);
//			clone.setIndAtivoPtc(indAtivoPtc);
//			clone.setDataInclusaoPtc(dataInclusaoPtc);
//			clone.setDataSolucaoPtc(dataSolucaoPtc);
//			clone.setDescricaoSolucaoPtc(descricaoSolucaoPtc);
//			clone.setIndAmbitoInternoGovPtc(indAmbitoInternoGovPtc);
//			clone.setDataLimitePtc(dataLimitePtc);
//			clone.setDataIdentificacaoPtc(dataIdentificacaoPtc);
//			clone.setDescricaoPtc(descricaoPtc);
//			clone.setItemEstruturaIett(itemEstruturaIett);
//			clone.setUsuarioUsu(usuarioUsu);
//			clone.setUsuarioUsuInclusao(usuarioUsuInclusao);
//			clone.setUsuarioUsuByCodUsuUltManutPtc(usuarioUsuByCodUsuUltManutPtc);
//			clone.setPontoCriticoCorPtccores(pontoCriticoCorPtccores);
//			clone.setApontamentoApts(apontamentoApts);
//			clone.setSisAtributoTipo(sisAtributoTipo);
//			clone.setAcompRelatorioArel(acompRelatorioArel);
//			clone.setHistoricoPtcHs(historicoPtcHs);

			return clone;

		} catch (CloneNotSupportedException e) {
			return null;
		}

	}

        /**
         *
         * @return
         */
        public Set<PontoCriticoSisAtributoPtcSatb> getPontoCriticoSisAtributoPtcSatbs() {
		return pontoCriticoSisAtributoPtcSatbs;
	}

        /**
         *
         * @param pontoCriticoSisAtributoPtcSatbs
         */
        public void setPontoCriticoSisAtributoPtcSatbs(Set<PontoCriticoSisAtributoPtcSatb> pontoCriticoSisAtributoPtcSatbs) {
		this.pontoCriticoSisAtributoPtcSatbs = pontoCriticoSisAtributoPtcSatbs;
	}

        /**
         *
         * @return
         */
        public Set<PontocriticoCorPtccor> getPontoCriticoCorPtccores() {
		return pontoCriticoCorPtccores;
	}

        /**
         *
         * @param pontoCriticoCorPtccores
         */
        public void setPontoCriticoCorPtccores(Set<PontocriticoCorPtccor> pontoCriticoCorPtccores) {
		this.pontoCriticoCorPtccores = pontoCriticoCorPtccores;
	}

        /**
         *
         * @return
         */
        public Set<ApontamentoApt> getApontamentoApts() {
		return apontamentoApts;
	}

        /**
         *
         * @param apontamentoApts
         */
        public void setApontamentoApts(Set<ApontamentoApt> apontamentoApts) {
		this.apontamentoApts = apontamentoApts;
	}

	
        /**
         *
         * @return
         */
        public List getAtributoEstrutura() {
    	return atributoEstrutura;
    }

	
    /**
     *
     * @param atributoEstrutura
     */
    public void setAtributoEstrutura(List atributoEstrutura) {
    	this.atributoEstrutura = atributoEstrutura;
    }

	
    /**
     *
     * @return
     */
    public SisGrupoAtributoSga getSisGrupoAtributoSga() {
    	return sisGrupoAtributoSga;
    }

	
    /**
     *
     * @param sisGrupoAtributoSga
     */
    public void setSisGrupoAtributoSga(SisGrupoAtributoSga sisGrupoAtributoSga) {
    	this.sisGrupoAtributoSga = sisGrupoAtributoSga;
    }

    /**
     *
     * @return
     */
    public UsuarioUsu getUsuarioUsuOperacao() {
		return usuarioUsuOperacao;
	}

        /**
         *
         * @param usuarioUsuOperacao
         */
        public void setUsuarioUsuOperacao(UsuarioUsu usuarioUsuOperacao) {
		this.usuarioUsuOperacao = usuarioUsuOperacao;
		
	}

}
