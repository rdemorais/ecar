package ecar.pojo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import comum.util.Pagina;

import ecar.dao.RegDemandaDao;
import ecar.dao.SisAtributoDao;
import ecar.dao.SisGrupoAtributoDao;
import ecar.dao.VisaoDao;
import ecar.exception.ECARException;

/**
 *
 * @author 70744416353
 */
public class AtributoDemandaAtbdem implements Serializable, ObjetoDemanda{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2503505744080947274L;

	/** identifier field */
    private Long codAtbdem;
    
    /** nullable persistent field */
    private String indAtivoAtbdem;
    
    /** nullable persistent field */
    private String labelPadraoAtbdem;

    /** nullable persistent field */
    private String nomeAtbdem;
    
    /** nullable persistent field */
    private String nomeFkAtbdem;
    
    /** nullable persistent field */
    private String codFkAtbdem;
        
    /** nullable persistent field */
    private String documentacaoAtbdem;
         
    /** nullable persistent field */
    private Integer tamanhoConteudoAtbdem;
      
    /*
    * ATRIBUTOS RELACIONADOS A VISAO
    * OBS: NÃO FOI CRIADA ENTIDADE ATRIBUTO VISAO POIS OS ATRIBUTOS DA DEMANDAS ATUAIS PERDE-SE-IAM QUANDO CRIADA NOVA VERSÃO DO ECAR
    * UTILIZANDO ATRIBUTO DEMANDA COM REFERÊNCIA A ENTIDADE VISAO PERMITE QUE OS ATRIBUTOS DE DEMANDA CONTINUEM A SER UTILIZADOS SENDO NECESSÁRIOS SOMENTE PREENCHAR DADOS RELACIONADOS A VISÃO
    * ENTÃO UM ATRIBUTO DE VISAO É UM ATRIBUTO DE DEMANDA QUANDO POSSUI VISÕES ASSOCIADAS
    */    
    // VISOES
    private Set<VisaoAtributoDemanda> visoesAtributoDemanda;
    
    /*
     * CAMPOS UTILIZADOS PARA MANUTENÇÃO DOS VALORES DE ATRIBUTOS ESCOLHIDOS
     */
    // GRUPO DE ATRIBUTO LIVRE SELECIONADO 
    private ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSga;  
    
    //  ATRIBUIDO LIVRE ASSOCIADO A DEMANDA 
    private ecar.pojo.SisAtributoSatb sisAtributoSatb;
    
    
    /** full constructor
     * @param codAtbdem
     * @param indAtivoAtbdem
     * @param nomeAtbdem
     * @param codFkAtbdem
     * @param nomeFkAtbdem
     * @param sisGrupoAtributoSga
     * @param documentacaoAtbdem
     * @param tamanhoConteudoAtbdem
     */
    public AtributoDemandaAtbdem(Long codAtbdem, String indAtivoAtbdem, String nomeAtbdem, String nomeFkAtbdem, String codFkAtbdem, ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSga, String documentacaoAtbdem, Integer tamanhoConteudoAtbdem) {
        
    	// PARAMETROS RELACIONADOS A "ATRIBUTO DE DEMANDA"
    	this.codAtbdem = codAtbdem;
        this.indAtivoAtbdem = indAtivoAtbdem;
        this.nomeAtbdem = nomeAtbdem;
        this.nomeFkAtbdem = nomeFkAtbdem;
        this.codFkAtbdem = codFkAtbdem;
        this.sisGrupoAtributoSga = sisGrupoAtributoSga;
        this.documentacaoAtbdem = documentacaoAtbdem;
        this.tamanhoConteudoAtbdem = tamanhoConteudoAtbdem;
    }

    /** full constructor
     * @param codAtbdem
     * @param indAtivoAtbdem
     * @param nomeAtbdem
     * @param codFkAtbdem
     * @param nomeFkAtbdem
     * @param sisGrupoAtributoSga
     * @param documentacaoAtbdem
     * @param tamanhoConteudoAtbdem
     */
    public AtributoDemandaAtbdem(Long codAtbdem, String indAtivoAtbdem, String nomeAtbdem, String nomeFkAtbdem, String codFkAtbdem, ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSga, SisAtributoSatb sisAtributoSatb, String documentacaoAtbdem, Integer tamanhoConteudoAtbdem, 
    		                     Set<VisaoAtributoDemanda> visoes) {
        
    	// PARAMETROS RELACIONADOS A "ATRIBUTO DE DEMANDA" 
    	this.codAtbdem = codAtbdem;
        this.indAtivoAtbdem = indAtivoAtbdem;
        this.nomeAtbdem = nomeAtbdem;
        this.nomeFkAtbdem = nomeFkAtbdem;
        this.codFkAtbdem = codFkAtbdem;
        this.sisGrupoAtributoSga = sisGrupoAtributoSga;      
        this.documentacaoAtbdem = documentacaoAtbdem;
        this.tamanhoConteudoAtbdem = tamanhoConteudoAtbdem;  	
    	// PARAMETROS ADICIONAIS
    	this.sisAtributoSatb = sisAtributoSatb;
    	this.visoesAtributoDemanda = visoes;
    }

    
    /** default constructor */
    public AtributoDemandaAtbdem() {
    }

    /** minimal constructor
     * @param codAtbdem
     */
    public AtributoDemandaAtbdem(Long codAtbdem) {
        this.codAtbdem = codAtbdem;
    }
    
    /**
     *
     * @return
     */
    public Long getCodAtbdem() {
		return codAtbdem;
	}

        /**
         *
         * @param codAtbdem
         */
        public void setCodAtbdem(Long codAtbdem) {
		this.codAtbdem = codAtbdem;
	}

        /**
         *
         * @return
         */
        public String getIndAtivoAtbdem() {
		return indAtivoAtbdem;
	}

        /**
         *
         * @param indAtivoAtbdem
         */
        public void setIndAtivoAtbdem(String indAtivoAtbdem) {
		this.indAtivoAtbdem = indAtivoAtbdem;
	}
	
        /**
         *
         * @return
         */
        public String getLabelPadraoAtbdem() {
		return labelPadraoAtbdem;
	}

        /**
         *
         * @param labelPadraoAtbdem
         */
        public void setLabelPadraoAtbdem(String labelPadraoAtbdem) {
		this.labelPadraoAtbdem = labelPadraoAtbdem;
	}

        /**
         *
         * @return
         */
        public String getNomeAtbdem() {
		return nomeAtbdem;
	}

        /**
         *
         * @param nomeAtbdem
         */
        public void setNomeAtbdem(String nomeAtbdem) {
		this.nomeAtbdem = nomeAtbdem;
	}

        /**
         *
         * @return
         */
        public String getNomeFkAtbdem() {
		return nomeFkAtbdem;
	}

        /**
         *
         * @param nomeFkAtbdem
         */
        public void setNomeFkAtbdem(String nomeFkAtbdem) {
		this.nomeFkAtbdem = nomeFkAtbdem;
	}

        /**
         *
         * @return
         */
        public String getCodFkAtbdem() {
		return codFkAtbdem;
	}

        /**
         *
         * @param codFkAtbdem
         */
        public void setCodFkAtbdem(String codFkAtbdem) {
		this.codFkAtbdem = codFkAtbdem;
	}

        /**
         *
         * @return
         */
        public ecar.pojo.SisGrupoAtributoSga getSisGrupoAtributoSga() {
		return sisGrupoAtributoSga;
	}

        /**
         *
         * @param sisGrupoAtributoSga
         */
        public void setSisGrupoAtributoSga(
			ecar.pojo.SisGrupoAtributoSga sisGrupoAtributoSga) {
		this.sisGrupoAtributoSga = sisGrupoAtributoSga;
	}

        /**
         *
         * @return
         */
        public String getDocumentacaoAtbdem() {
		return documentacaoAtbdem;
	}

        /**
         *
         * @param documentacaoAtbdem
         */
        public void setDocumentacaoAtbdem(String documentacaoAtbdem) {
		this.documentacaoAtbdem = documentacaoAtbdem;
	}

	public Integer getTamanhoConteudoAtbdem() {
		return tamanhoConteudoAtbdem;
	}

        /**
         *
         * @param tamanhoConteudoAtbdem
         */
        public void setTamanhoConteudoAtbdem(Integer tamanhoConteudoAtbdem) {
		this.tamanhoConteudoAtbdem = tamanhoConteudoAtbdem;
	}

	/**
     * @author N/C
	 * @since N/C 
	 * @return String
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("codAtbdem", getCodAtbdem())
            .toString();
    }
    
    /**
     * @author N/C
	 * @since N/C 
     * @param other
	 * @return boolean
     */
    @Override
    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof AtributoDemandaAtbdem) ) return false;
        AtributoDemandaAtbdem castOther = (AtributoDemandaAtbdem) other;
        return new EqualsBuilder()
            .append(this.getCodAtbdem(), castOther.getCodAtbdem())
            .isEquals();
    }
    
    /**
     * @author N/C
	 * @since N/C 
	 * @return int
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodAtbdem())
            .toHashCode();
    }

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoDemanda#iGetLabel()
     */
	public String iGetCodFk() {
		return this.getCodFkAtbdem();
	}
	
	public String iGetDica(Long codVisao) {
		
		Iterator<VisaoAtributoDemanda> it = visoesAtributoDemanda.iterator();
		VisaoAtributoDemanda visaoAtributoDemanda = null;
		
		while(it.hasNext()) {
			visaoAtributoDemanda = it.next();
			if (visaoAtributoDemanda.getVisaoAtributoDemandaPk().getVisao().getCodVisao().equals(codVisao)) {
				return visaoAtributoDemanda.getDicaAtbvis();
			}
		}
		return VisaoAtributoDemanda.VALOR_DEFAULT_DICA;
	}

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoDemanda#iGetGrupoAtributosLivres()
     */
	public SisGrupoAtributoSga iGetGrupoAtributosLivres() {
		return this.getSisGrupoAtributoSga();
	}

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoDemanda#iGetLabel()
     */
	public String iGetLabel() {
		return this.getLabelPadraoAtbdem();
	}
	
	public Integer iGetLargura(){
		return ObjetoDemanda.DEFAULT_LARGURA_TELA_CAMPO;
	}

	
	/* (non-Javadoc)
     * @see ecar.pojo.ObjetoDemanda#iGetLargura(Long codVisao)
     */
	public Integer iGetLargura(Long codVisao) {
		
		Iterator<VisaoAtributoDemanda> it = visoesAtributoDemanda.iterator();
		VisaoAtributoDemanda visaoAtributoDemanda = null;
		
		while(it.hasNext()) {
			visaoAtributoDemanda = it.next();
			if (visaoAtributoDemanda.getVisaoAtributoDemandaPk().getVisao().getCodVisao().equals(codVisao)) {
				return visaoAtributoDemanda.getLarguraListagemTelaAtbvis();
			}
		}
		return ObjetoDemanda.DEFAULT_LARGURA_TELA_CAMPO;
		
	}
	
	/* (non-Javadoc)
     * @see ecar.pojo.ObjetoDemanda#iGetLargura(Long codVisao)
     */
        /**
         *
         * @param codVisao
         * @return
         */
	public String recuperarEditavel(Long codVisao) {
		
		Iterator<VisaoAtributoDemanda> it = visoesAtributoDemanda.iterator();
		VisaoAtributoDemanda visaoAtributoDemanda = null;
		
		while(it.hasNext()) {
			visaoAtributoDemanda = it.next();
			if (visaoAtributoDemanda.getVisaoAtributoDemandaPk().getVisao().getCodVisao().equals(codVisao)) {
				return visaoAtributoDemanda.getIndEditavelAtbvis();
			}
		}
			
		return VisaoAtributoDemanda.VALOR_DEFAULT_EH_EDITAVEL;
		
	}
	
//	/* (non-Javadoc)
//     * @see ecar.pojo.ObjetoDemanda#iGetLargura(Long codVisao)
//     */
//	public String getExibivel(Long codVisao) {
//		
//		Iterator<VisaoAtributoDemanda> it = visoesAtributoDemanda.iterator();
//		VisaoAtributoDemanda visaoAtributoDemanda = null;
//		
//		while(it.hasNext()) {
//			visaoAtributoDemanda = it.next();
//			if (visaoAtributoDemanda.getVisaoAtributoDemandaPk().getVisao().getCodVisao().equals(codVisao)) {
//				return visaoAtributoDemanda.getIndEditavelAtbvis();
//			}
//		}
//			
//		return VisaoAtributoDemanda.VALOR_DEFAULT_EH_EDITAVEL;
//		
//	}


    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoDemanda#iGetNome()
     */
	public String iGetNome() {
		
		return this.getNomeAtbdem();
	}

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoDemanda#iGetNomeFk()
     */
	public String iGetNomeFk() {
		return this.getNomeFkAtbdem();
	}

	/**
     * Retorna verdadeiro ou falso dependendo da obrigatoriedade do objeto
         * @param codVisao
     * @return Boolean(true) ou Boolean(false)
     */
    public Boolean iGetObrigatorio(Long codVisao) {
    	
		Iterator<VisaoAtributoDemanda> it = visoesAtributoDemanda.iterator();
		VisaoAtributoDemanda visaoAtributoDemanda = null;
		
		while(it.hasNext()) {
			visaoAtributoDemanda = it.next();
			if (visaoAtributoDemanda.getVisaoAtributoDemandaPk().getVisao().getCodVisao().equals(codVisao)) {
				return "S".equals(visaoAtributoDemanda.getIndObrigatorioAtbvis())?true:false;
			}
		}
			
		return VisaoAtributoDemanda.VALOR_DEFAULT_EH_OBRIGATORIO;
    }
    
	/**
     * Retorna verdadeiro ou falso dependendo da obrigatoriedade do objeto
     * @param codVisao
     * @return Boolean(true) ou Boolean(false)
     */
    public Boolean iGetExibivel(Long codVisao) {
    	
		Iterator<VisaoAtributoDemanda> it = visoesAtributoDemanda.iterator();
		VisaoAtributoDemanda visaoAtributoDemanda = null;
		
		while(it.hasNext()) {
			visaoAtributoDemanda = it.next();
			if (visaoAtributoDemanda.getVisaoAtributoDemandaPk().getVisao().getCodVisao().equals(codVisao)) {
				return "S".equals(visaoAtributoDemanda.getIndExibivelAtbvis())?true:false;
			}
		}
			
		return VisaoAtributoDemanda.VALOR_DEFAULT_EH_EXIBIVEL;
    }
	
	/**
     * Retorna verdadeiro ou falso dependendo se o atributo demanda eh exibel ou nao numa visao
     * @param codVisao
     * @return Boolean(true) ou Boolean(false)
     */
    public Boolean iGetExibivelConsulta(Long codVisao) {
    	
		Iterator<VisaoAtributoDemanda> it = visoesAtributoDemanda.iterator();
		VisaoAtributoDemanda visaoAtributoDemanda = null;
		
		while(it.hasNext()) {
			visaoAtributoDemanda = it.next();
			if (visaoAtributoDemanda.getVisaoAtributoDemandaPk().getVisao().getCodVisao().equals(codVisao)) {
				return "S".equals(visaoAtributoDemanda.getIndExibivelAtbvis())?true:false;
			}
		}
			
		return VisaoAtributoDemanda.VALOR_DEFAULT_EH_EXIBIVEL;
    }
    
    

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoDemanda#iGetTamanhoConteudoAtbdem()
     */
	public Integer iGetTamanhoConteudoAtbdem() {
		if (tamanhoConteudoAtbdem != null && tamanhoConteudoAtbdem != 0) {
			return tamanhoConteudoAtbdem;
		} else {
			return DEFAULT_TAMANHO_CAMPO_TEXT;
		}
	}

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoDemanda#iGetTipo()
     */
	public Class iGetTipo() {
		return this.getClass();
	}

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoDemanda#iGetValor()
     */
	public String iGetValor(RegDemandaRegd regDemanda) throws ECARException {
		
		if (this.getNomeAtbdem().equals("localDemandaLdems")){
			//deve aparecer na tela o nome do município e o nome do Estado. 
			return iGetValoresCodFk(regDemanda);
			//return new RegDemandaDao(null).getValorLocaisDemanda(regDemanda, 
	        //        this.getNomeFkAtbdem());
		}
		if (this.getNomeAtbdem().equals("entidadeDemandaEntds")){
			return new RegDemandaDao(null).getValorEntidadesDemanda(regDemanda, 
	                this.getNomeFkAtbdem());
		}
		if (this.getNomeAtbdem().equals("entidadeOrgaoDemandaEntorgds")){
			return new RegDemandaDao(null).getValorEntidadesOrgaosDemanda(regDemanda, 
	                this.getNomeFkAtbdem());
		}
		
		return new RegDemandaDao(null).getValorAtributoDemanda(regDemanda, 
                this.getNomeAtbdem(), this.getNomeFkAtbdem());
				
	}
	
	public String iGetHierarquiaLocaisDemanda(RegDemandaRegd regDemanda) throws ECARException {
		
		
		return new RegDemandaDao(null).getHierarquiaLocaisDemanda(regDemanda);
				
	}

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoDemanda#iGetValorCodFk()
     */
	public String iGetValorCodFk(RegDemandaRegd regDemanda)
			throws ECARException {
		
		if (this.getNomeAtbdem().equals("localDemandaLdems")){
			return new RegDemandaDao(null).getValorLocaisDemanda(regDemanda, 
	                this.getCodFkAtbdem());
		}
		if (this.getNomeAtbdem().equals("entidadeDemandaEntds")){
			return new RegDemandaDao(null).getValorEntidadesDemanda(regDemanda, 
	                this.getCodFkAtbdem());
		}
		if (this.getNomeAtbdem().equals("entidadeOrgaoDemandaEntorgds")){
			return new RegDemandaDao(null).getValorEntidadesOrgaosDemanda(regDemanda, 
	                this.getCodFkAtbdem());
		}
		
		return new RegDemandaDao(null).getValorAtributoDemanda(regDemanda, 
                this.getNomeAtbdem(), this.getCodFkAtbdem());
					
	}

    /* (non-Javadoc)
     * @see ecar.pojo.ObjetoDemanda#iGetValoresCodFk()
     */
	public String iGetValoresCodFk(RegDemandaRegd regDemanda)
			throws ECARException {
		
		if (this.nomeAtbdem.equals("localDemandaLdems")){		
			return new RegDemandaDao(null).getHierarquiaLocaisDemanda(regDemanda);
		}
		
		return null;
		
	}

	public Long iGetCodAtbdem() {
		return codAtbdem;
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
	 * Método que mapeia objeto do Formulario em objeto negócio
	 * @param request
	 * @param atributoDemanda
	 * @param usarGetParamStr
	 * @param ehVisao
	 * @throws ECARException
	 */
	public static void mapearObjetoNegocio(HttpServletRequest request, AtributoDemandaAtbdem atributoDemanda, boolean usarGetParamStr, boolean ehVisao) throws ECARException {
		
		String hidAcao = Pagina.getParam(request, "hidAcao");
		String sDefault = Pagina.NAO;
		if (hidAcao!=null && hidAcao.contains("pesquisar")) {
			sDefault = null;
		}
		    	
		if (!ehVisao) {
		
			if(Pagina.getParam(request, "codigo") != null)
	    		atributoDemanda.setCodAtbdem(Long.valueOf (Pagina.getParam(request, "codigo")));
	    	else
	    		atributoDemanda.setCodAtbdem(null);
			
	    	if(usarGetParamStr){
	    		atributoDemanda.setIndAtivoAtbdem(Pagina.getParamOrDefault(request, "indAtivoAtbdem", sDefault));
	    		atributoDemanda.setNomeAtbdem(Pagina.getParamStr(request, "nomeAtbdem").trim());
	    		atributoDemanda.setLabelPadraoAtbdem(Pagina.getParamStr(request, "labelPadraoAtbdem").trim());
	    		atributoDemanda.setCodFkAtbdem(Pagina.getParamStr(request, "codFkAtbdem").trim());
	    		atributoDemanda.setNomeFkAtbdem(Pagina.getParamStr(request, "nomeFkAtbdem").trim());
	    		atributoDemanda.setDocumentacaoAtbdem(Pagina.getParamStr(request,"documentacaoAtbdem").trim());    		
	    	}
	    	else{
	    		atributoDemanda.setIndAtivoAtbdem(Pagina.getParam(request, "indAtivoAtbdem"));
	    		atributoDemanda.setNomeAtbdem(Pagina.getParam(request, "nomeAtbdem"));
	    		atributoDemanda.setLabelPadraoAtbdem(Pagina.getParam(request, "labelPadraoAtbdem"));
	    		atributoDemanda.setCodFkAtbdem(Pagina.getParam(request, "codFkAtbdem"));
	    		atributoDemanda.setNomeFkAtbdem(Pagina.getParam(request, "nomeFkAtbdem"));
	    		atributoDemanda.setDocumentacaoAtbdem(Pagina.getParam(request,"documentacaoAtbdem"));
	    	}
	    	    	    	
	    	//tamanhoConteudoAtbdem
	    	if(Pagina.getParam(request, "tamanhoConteudoAtbdem") != null)
	    		atributoDemanda.setTamanhoConteudoAtbdem((Integer.valueOf(Pagina.getParam(request, "tamanhoConteudoAtbdem"))));
	    	else
	    		atributoDemanda.setTamanhoConteudoAtbdem(null);
	
	
	    	SisGrupoAtributoDao sgaDao = new SisGrupoAtributoDao(request);
	    	SisAtributoDao saDao  = new SisAtributoDao(request);
	    	
			// mapeia o grupo
			if(Pagina.getParam(request, "sisGrupoAtributoSga") != null){
	    		atributoDemanda.setSisGrupoAtributoSga((SisGrupoAtributoSga)sgaDao.buscar(SisGrupoAtributoSga.class, Long.valueOf(Pagina.getParamStr(request, "sisGrupoAtributoSga"))));
	    	}
	    	else{
	    		atributoDemanda.setSisGrupoAtributoSga(null);
	    	}

		} else {
    		
			VisaoAtributoDemanda visaoAtributoDemanda = null;
			VisaoAtributoDemandaPK visaoAtributoDemandaPK = null;
			VisaoAtributoDemanda.mapearObjetoNegocio(request, visaoAtributoDemanda);
			HashSet<VisaoAtributoDemanda> visoesAtdem = new HashSet<VisaoAtributoDemanda>();
	    	// visoes escolhidas
	    	VisaoDao visDao = new VisaoDao(request);
	    	
	    	if (visDao.getVisoes(Pagina.getParamArray(request, "visoes"))!=null) {
	    		
	    		Iterator<VisaoDemandasVisDem> it = visDao.getVisoes(Pagina.getParamArray(request, "visoes")).iterator();
	    		
	    		while(it.hasNext()) {
	    			visaoAtributoDemanda = new VisaoAtributoDemanda();
	    			visaoAtributoDemandaPK = new VisaoAtributoDemandaPK();
	    			visaoAtributoDemandaPK.setAtributoDemanda(atributoDemanda);
	    			visaoAtributoDemandaPK.setVisao(it.next());
	    			visaoAtributoDemanda.setVisaoAtributoDemandaPk(visaoAtributoDemandaPK);
	    			VisaoAtributoDemanda.mapearObjetoNegocio(request, visaoAtributoDemanda);
	    			visoesAtdem.add(visaoAtributoDemanda);
	    		}
	    		
	    		atributoDemanda.setVisoesAtributoDemanda(visoesAtdem);
	    	}	    	
    	}

	}

        /**
         *
         * @return
         */
        public Set<VisaoAtributoDemanda> getVisoesAtributoDemanda() {
		return visoesAtributoDemanda;
	}

        /**
         *
         * @param visoesAtributoDemanda
         */
        public void setVisoesAtributoDemanda(Set<VisaoAtributoDemanda> visoesAtributoDemanda) {
		this.visoesAtributoDemanda = visoesAtributoDemanda;
	}
	
	public String iGetIndEditavel(Long codVisao){
		
		String editavel = "S";
		
		Iterator<VisaoAtributoDemanda> it = visoesAtributoDemanda.iterator();
		VisaoAtributoDemanda visaoAtributoDemanda = null;
		
		while(it.hasNext()) {
			visaoAtributoDemanda = it.next();
			if (visaoAtributoDemanda.getVisaoAtributoDemandaPk().getVisao().getCodVisao().equals(codVisao)) {
				editavel = visaoAtributoDemanda.getIndEditavelAtbvis();
				break;
			}
		}
		
		return editavel;
	}
	

}
