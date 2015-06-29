package ecar.pojo;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import comum.util.Pagina;

/**
 *
 * @author 70744416353
 */
public class VisaoAtributoDemanda implements Serializable {
	
	private static final long serialVersionUID = -5549149190114022412L;
		
    private VisaoAtributoDemandaPK visaoAtributoDemandaPk;
    
    // OBRIGATORIO NA VISAO
    private String indObrigatorioAtbvis;
    
    // SEQUENCIA NA LISTAGEM NA VISAO
    private Integer seqApresListagemTelaAtbvis;
    
    // LARGURA NA VISAO
    private Integer larguraListagemTelaAtbvis;
    
    // SEQUENCIA TELA NA VISAO
    private Integer seqApresTelaCampoAtbvis;
    
    // DICA NA VISAO
    private String dicaAtbvis;
    
    // LISTA NA VISAO
    private String indListagemItensAtbvis;
        
    // EXIBIVEL NA VISAO
    private String indExibivelAtbvis;
    
    // EXIBIVEL NA VISAO
    private String indExibivelConsultaAtbvis;
    
    // EDITAVEL NA VISAO
    private String indEditavelAtbvis;
    
    // FILTRO NA VISAO
    private String indFiltroAtbvis;
    
    // RESTRITIVO
    private String indRestritivo;
    
    /**
     *
     */
    public static final String  VALOR_DEFAULT_EH_EDITAVEL = "S";
    /**
     *
     */
    public static final Boolean VALOR_DEFAULT_EH_EXIBIVEL = true;
    /**
     *
     */
    public static final Boolean VALOR_DEFAULT_EH_OBRIGATORIO = true;
    /**
     *
     */
    public static final String VALOR_DEFAULT_DICA = "";

    /**
     *
     */
    public VisaoAtributoDemanda() {
    }

    /**
     *
     * @return
     */
    public VisaoAtributoDemandaPK getVisaoAtributoDemandaPk() {
		return visaoAtributoDemandaPk;
	}

        /**
         *
         * @param visaoAtributoDemandaPk
         */
        public void setVisaoAtributoDemandaPk(VisaoAtributoDemandaPK visaoAtributoDemandaPk) {
		this.visaoAtributoDemandaPk = visaoAtributoDemandaPk;
	}

        /**
         *
         * @return
         */
        public String getIndObrigatorioAtbvis() {
		return indObrigatorioAtbvis;
	}

        /**
         *
         * @param indObrigatorioAtbvis
         */
        public void setIndObrigatorioAtbvis(String indObrigatorioAtbvis) {
		this.indObrigatorioAtbvis = indObrigatorioAtbvis;
	}

        /**
         *
         * @return
         */
        public Integer getSeqApresListagemTelaAtbvis() {
		return seqApresListagemTelaAtbvis;
	}

        /**
         *
         * @param seqApresListagemTelaAtbvis
         */
        public void setSeqApresListagemTelaAtbvis(Integer seqApresListagemTelaAtbvis) {
		this.seqApresListagemTelaAtbvis = seqApresListagemTelaAtbvis;
	}

        /**
         *
         * @return
         */
        public Integer getLarguraListagemTelaAtbvis() {
		return larguraListagemTelaAtbvis;
	}

        /**
         *
         * @param larguraListagemTelaAtbvis
         */
        public void setLarguraListagemTelaAtbvis(Integer larguraListagemTelaAtbvis) {
		this.larguraListagemTelaAtbvis = larguraListagemTelaAtbvis;
	}

        /**
         *
         * @return
         */
        public Integer getSeqApresTelaCampoAtbvis() {
		return seqApresTelaCampoAtbvis;
	}

        /**
         *
         * @param seqApresTelaCampoAtbvis
         */
        public void setSeqApresTelaCampoAtbvis(Integer seqApresTelaCampoAtbvis) {
		this.seqApresTelaCampoAtbvis = seqApresTelaCampoAtbvis;
	}

        /**
         *
         * @return
         */
        public String getDicaAtbvis() {
		return dicaAtbvis;
	}

        /**
         *
         * @param dicaAtbvis
         */
        public void setDicaAtbvis(String dicaAtbvis) {
		this.dicaAtbvis = dicaAtbvis;
	}

        /**
         *
         * @return
         */
        public String getIndListagemItensAtbvis() {
		return indListagemItensAtbvis;
	}

        /**
         *
         * @param indListagemItensAtbvis
         */
        public void setIndListagemItensAtbvis(String indListagemItensAtbvis) {
		this.indListagemItensAtbvis = indListagemItensAtbvis;
	}

        /**
         *
         * @return
         */
        public String getIndExibivelAtbvis() {
		return indExibivelAtbvis;
	}

        /**
         *
         * @param indExibivelAtbvis
         */
        public void setIndExibivelAtbvis(String indExibivelAtbvis) {
		this.indExibivelAtbvis = indExibivelAtbvis;
	}

        /**
         *
         * @return
         */
        public String getIndEditavelAtbvis() {
		return indEditavelAtbvis;
	}

        /**
         *
         * @param indEditavelAtbvis
         */
        public void setIndEditavelAtbvis(String indEditavelAtbvis) {
		this.indEditavelAtbvis = indEditavelAtbvis;
	}

        /**
         *
         * @return
         */
        public String getIndFiltroAtbvis() {
		return indFiltroAtbvis;
	}

        /**
         *
         * @param indFiltroAtbvis
         */
        public void setIndFiltroAtbvis(String indFiltroAtbvis) {
		this.indFiltroAtbvis = indFiltroAtbvis;
	}
	
        /**
         *
         * @return
         */
        public String getIndExibivelConsultaAtbvis() {
		return indExibivelConsultaAtbvis;
	}

        /**
         *
         * @param indExibivelConsultaAtbvis
         */
        public void setIndExibivelConsultaAtbvis(String indExibivelConsultaAtbvis) {
		this.indExibivelConsultaAtbvis = indExibivelConsultaAtbvis;
	}

	/**
	 * Método que mapeia objeto do Formulario em objeto negócio
	 * @param request
         * @param visaoAtributoDemanda
	 */
	public static void mapearObjetoNegocio(HttpServletRequest request, VisaoAtributoDemanda visaoAtributoDemanda) {
		
		String hidAcao = Pagina.getParam(request, "hidAcao");
		String sDefault = Pagina.NAO;
		
		if (hidAcao!=null && hidAcao.contains("pesquisar")) {
			sDefault = null;
		}
		
		visaoAtributoDemanda.setIndObrigatorioAtbvis((Pagina.getParamOrDefault(request, "indObrigatorioAtbvis", sDefault)));
		visaoAtributoDemanda.setDicaAtbvis((Pagina.getParam(request,"dicaAtbvis")));
		visaoAtributoDemanda.setIndListagemItensAtbvis(((Pagina.getParamOrDefault(request, "indListagemItensAtbvis", sDefault))));
		visaoAtributoDemanda.setIndFiltroAtbvis((((Pagina.getParamOrDefault(request, "indFiltroAtbvis", sDefault)))));
		if(Pagina.getParamStr(request, "indEditavelAtbvis") != null && !"".equals(Pagina.getParamStr(request, "indEditavelAtbvis"))){
			visaoAtributoDemanda.setIndEditavelAtbvis((((Pagina.getParamOrDefault(request, "indEditavelAtbvis", sDefault)))));
		} else{
			visaoAtributoDemanda.setIndEditavelAtbvis((((Pagina.getParamOrDefault(request, "indEditavelAtbvisHidden", sDefault)))));
		}
		if(Pagina.getParamStr(request, "indExibivelAtbvis") != null && !"".equals(Pagina.getParamStr(request, "indExibivelAtbvis"))){
			visaoAtributoDemanda.setIndExibivelAtbvis((((Pagina.getParamOrDefault(request, "indExibivelAtbvis", sDefault)))));
		} else{
			visaoAtributoDemanda.setIndExibivelAtbvis((((Pagina.getParamOrDefault(request, "indExibivelAtbvisHidden", sDefault)))));
		}
		visaoAtributoDemanda.setIndExibivelConsultaAtbvis((((Pagina.getParamOrDefault(request, "indExibivelConsultaAtbvis", sDefault)))));
		visaoAtributoDemanda.setIndRestritivo((((Pagina.getParamOrDefault(request, "indRestritivo", sDefault)))));
		
		//seqApresListagemTelaAtbvis
    	if(Pagina.getParam(request, "seqApresListagemTelaAtbvis") != null)
    		visaoAtributoDemanda.setSeqApresListagemTelaAtbvis((Integer.valueOf(Pagina.getParam(request, "seqApresListagemTelaAtbvis"))));
    	else
    		visaoAtributoDemanda.setSeqApresListagemTelaAtbvis(null);
    	
    	
    	//seqApresTelaCampoAtbvis
    	if(Pagina.getParam(request, "seqApresTelaCampoAtbvis") != null)
    		visaoAtributoDemanda.setSeqApresTelaCampoAtbvis((Integer.valueOf(Pagina.getParam(request, "seqApresTelaCampoAtbvis"))));
    	else
    		visaoAtributoDemanda.setSeqApresTelaCampoAtbvis(null);
    	

    	//larguraListagemTelaAtbvis
    	if(Pagina.getParam(request, "larguraListagemTelaAtbvis") != null)
    		visaoAtributoDemanda.setLarguraListagemTelaAtbvis((Integer.valueOf(Pagina.getParam(request, "larguraListagemTelaAtbvis"))));
    	else
    		visaoAtributoDemanda.setLarguraListagemTelaAtbvis(null);
	}

        /**
         *
         * @return
         */
        public String getIndRestritivo() {
		return indRestritivo;
	}

        /**
         *
         * @param indRestritivo
         */
        public void setIndRestritivo(String indRestritivo) {
		this.indRestritivo = indRestritivo;
	}
    
}
