package ecar.pojo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import comum.util.Pagina;

import ecar.dao.SituacaoDao;
import ecar.exception.ECARException;


/**
 *
 * @author 70744416353
 */
public class VisaoDemandasVisDem implements Serializable {

	private static final long serialVersionUID = -4001671150180361250L;

    private Long codVisao;

    private String descricaoVisao;

    //private Set<RegDemandaRegd> demandas;

    private String visualizarDemandasIncluidasUsuario;
    
    private String utilizarRegraEntidadeSolucionadora;
    
    
    // permissoes
    private String ehPermitidoIncluirDemanda;
    
    private String ehPermitidoAlterarDemanda;
    
    private String ehPermitidoExcluirDemanda;
    
    private String ehPermitidoIncluirApontamento;
    
    private String ehPermitidoAlterarApontamento;
    
    private String ehPermitidoExcluirApontamento;

    private String ehPermitidoIncluirAnexoApontamento;
    
    private String ehPermitidoAlterarAnexoApontamento;
    
    private String ehPermitidoExcluirAnexoApontamento;
    
    private String ehPermitidoIncluirAnexoDemanda;
    
    private String ehPermitidoAlterarAnexoDemanda;
    
    private String ehPermitidoExcluirAnexoDemanda;
    
    // usuario inclusao
    private ecar.pojo.UsuarioUsu usuarioInclusao;
    
    // VISOES grupo de acesso
    private Set<VisaoDemandasGrpAcesso> visoesGrpAcesso;
     
    private Set<VisaoSituacaoDemanda> visaoSituacaoDemandas = new HashSet<VisaoSituacaoDemanda>();
    
    
    
    /**
     *
     */
    public static final String VISAO_SELECIONADA = "VISAO_SELECIONADA";
    

    /**
     *
     * @param codVisao
     * @param descricaoVisao
     */
    public VisaoDemandasVisDem(Long codVisao, String descricaoVisao){//, Set<RegDemandaRegd> demandas) {
        this.codVisao = codVisao;
        this.descricaoVisao = descricaoVisao;
        //this.demandas = demandas;
    }

    /**
     *
     */
    public VisaoDemandasVisDem() {
    }

    /**
     *
     * @return
     */
    public Long getCodVisao() {
		return codVisao;
	}

        /**
         *
         * @param codVisao
         */
        public void setCodVisao(Long codVisao) {
		this.codVisao = codVisao;
	}

        /**
         *
         * @return
         */
        public String getDescricaoVisao() {
		return descricaoVisao;
	}

        /**
         *
         * @param descricaoVisao
         */
        public void setDescricaoVisao(String descricaoVisao) {
		this.descricaoVisao = descricaoVisao;
	}

//	public Set<RegDemandaRegd> getDemandas() {
//		return demandas;
//	}
//
//	public void setDemandas(Set<RegDemandaRegd> demandas) {
//		this.demandas = demandas;
//	}

        /**
         *
         * @return
         */
        public String getEhPermitidoIncluirDemanda() {
		return ehPermitidoIncluirDemanda;
	}

        /**
         *
         * @param ehPermitidoIncluirDemanda
         */
        public void setEhPermitidoIncluirDemanda(String ehPermitidoIncluirDemanda) {
		this.ehPermitidoIncluirDemanda = ehPermitidoIncluirDemanda;
	}

        /**
         *
         * @return
         */
        public String getEhPermitidoAlterarDemanda() {
		return ehPermitidoAlterarDemanda;
	}

        /**
         *
         * @param ehPermitidoAlterarDemanda
         */
        public void setEhPermitidoAlterarDemanda(String ehPermitidoAlterarDemanda) {
		this.ehPermitidoAlterarDemanda = ehPermitidoAlterarDemanda;
	}

        /**
         *
         * @return
         */
        public String getEhPermitidoExcluirDemanda() {
		return ehPermitidoExcluirDemanda;
	}

        /**
         *
         * @param ehPermitidoExcluirDemanda
         */
        public void setEhPermitidoExcluirDemanda(String ehPermitidoExcluirDemanda) {
		this.ehPermitidoExcluirDemanda = ehPermitidoExcluirDemanda;
	}

        /**
         *
         * @return
         */
        public String getEhPermitidoIncluirApontamento() {
		return ehPermitidoIncluirApontamento;
	}

        /**
         *
         * @param ehPermitidoIncluirApontamento
         */
        public void setEhPermitidoIncluirApontamento(String ehPermitidoIncluirApontamento) {
		this.ehPermitidoIncluirApontamento = ehPermitidoIncluirApontamento;
	}

        /**
         *
         * @return
         */
        public String getEhPermitidoAlterarApontamento() {
		return ehPermitidoAlterarApontamento;
	}

        /**
         *
         * @param ehPermitidoAlterarApontamento
         */
        public void setEhPermitidoAlterarApontamento(String ehPermitidoAlterarApontamento) {
		this.ehPermitidoAlterarApontamento = ehPermitidoAlterarApontamento;
	}

        /**
         *
         * @return
         */
        public String getEhPermitidoExcluirApontamento() {
		return ehPermitidoExcluirApontamento;
	}

        /**
         *
         * @param ehPermitidoExcluirApontamento
         */
        public void setEhPermitidoExcluirApontamento(String ehPermitidoExcluirApontamento) {
		this.ehPermitidoExcluirApontamento = ehPermitidoExcluirApontamento;
	}

        /**
         *
         * @return
         */
        public String getEhPermitidoIncluirAnexoApontamento() {
		return ehPermitidoIncluirAnexoApontamento;
	}

        /**
         *
         * @param ehPermitidoIncluirAnexoApontamento
         */
        public void setEhPermitidoIncluirAnexoApontamento(String ehPermitidoIncluirAnexoApontamento) {
		this.ehPermitidoIncluirAnexoApontamento = ehPermitidoIncluirAnexoApontamento;
	}

        /**
         *
         * @return
         */
        public String getEhPermitidoAlterarAnexoApontamento() {
		return ehPermitidoAlterarAnexoApontamento;
	}

        /**
         *
         * @param ehPermitidoAlterarAnexoApontamento
         */
        public void setEhPermitidoAlterarAnexoApontamento(String ehPermitidoAlterarAnexoApontamento) {
		this.ehPermitidoAlterarAnexoApontamento = ehPermitidoAlterarAnexoApontamento;
	}

        /**
         *
         * @return
         */
        public String getEhPermitidoExcluirAnexoApontamento() {
		return ehPermitidoExcluirAnexoApontamento;
	}

        /**
         *
         * @param ehPermitidoExcluirAnexoApontamento
         */
        public void setEhPermitidoExcluirAnexoApontamento(String ehPermitidoExcluirAnexoApontamento) {
		this.ehPermitidoExcluirAnexoApontamento = ehPermitidoExcluirAnexoApontamento;
	}
	
	


	/**
	 * Método que mapeia objeto do Formulario em objeto negócio
	 * @param request
         * @param visao
	 * @throws ECARException
	 */
	public static void mapearObjetoNegocio(HttpServletRequest request, VisaoDemandasVisDem visao) throws ECARException {

		SituacaoDao sitDao = new SituacaoDao(request);
		String hidAcao = Pagina.getParam(request, "hidAcao");
		String sDefault = Pagina.NAO;
		if (hidAcao!=null && hidAcao.contains("pesquisar")) {
			sDefault = null;
		}
		
		if ((Pagina.getParamStr(request,"descricaoVisao").trim())!=null && (Pagina.getParamStr(request,"descricaoVisao").trim()).length()>0)
			visao.setDescricaoVisao((Pagina.getParamStr(request,"descricaoVisao").trim()));
		
		visao.setEhPermitidoIncluirDemanda(Pagina.getParamOrDefault(request, "ehPermitidoIncluirDemanda", sDefault));
		visao.setEhPermitidoAlterarDemanda(Pagina.getParamOrDefault(request, "ehPermitidoAlterarDemanda", sDefault));
		visao.setEhPermitidoExcluirDemanda(Pagina.getParamOrDefault(request, "ehPermitidoExcluirDemanda", sDefault));
		
		visao.setEhPermitidoIncluirApontamento(Pagina.getParamOrDefault(request, "ehPermitidoIncluirApontamento", sDefault));
		visao.setEhPermitidoAlterarApontamento(Pagina.getParamOrDefault(request, "ehPermitidoAlterarApontamento", sDefault));
		visao.setEhPermitidoExcluirApontamento(Pagina.getParamOrDefault(request, "ehPermitidoExcluirApontamento", sDefault));
		
		visao.setEhPermitidoIncluirAnexoApontamento(Pagina.getParamOrDefault(request, "ehPermitidoIncluirAnexoApontamento", sDefault));
		visao.setEhPermitidoAlterarAnexoApontamento(Pagina.getParamOrDefault(request, "ehPermitidoAlterarAnexoApontamento", sDefault));
		visao.setEhPermitidoExcluirAnexoApontamento(Pagina.getParamOrDefault(request, "ehPermitidoExcluirAnexoApontamento", sDefault));
				
		visao.setEhPermitidoIncluirAnexoDemanda(Pagina.getParamOrDefault(request, "ehPermitidoIncluirAnexoDemanda", sDefault));
		visao.setEhPermitidoAlterarAnexoDemanda(Pagina.getParamOrDefault(request, "ehPermitidoAlterarAnexoDemanda", sDefault));
		visao.setEhPermitidoExcluirAnexoDemanda(Pagina.getParamOrDefault(request, "ehPermitidoExcluirAnexoDemanda", sDefault));
		
		
		visao.setVisualizarDemandasIncluidasUsuario(Pagina.getParamOrDefault(request, "visualizarDemandasIncluidasUsuario", sDefault));
		visao.setUtilizarRegraEntidadeSolucionadora(Pagina.getParamOrDefault(request, "utilizarRegraEntidadeSolucionadora", sDefault));
		
    	if (sDefault != null){
    		visao.setUsuarioInclusao(((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario());
    	}
		
		
	}

        /**
         *
         * @return
         */
        public String getVisualizarDemandasIncluidasUsuario() {
		return visualizarDemandasIncluidasUsuario;
	}

        /**
         *
         * @param visualizarDemandasIncluidasUsuario
         */
        public void setVisualizarDemandasIncluidasUsuario(String visualizarDemandasIncluidasUsuario) {
		this.visualizarDemandasIncluidasUsuario = visualizarDemandasIncluidasUsuario;
	}

        /**
         *
         * @return
         */
        public String getUtilizarRegraEntidadeSolucionadora() {
		return utilizarRegraEntidadeSolucionadora;
	}

        /**
         *
         * @param utilizarRegraEntidadeSolucionadora
         */
        public void setUtilizarRegraEntidadeSolucionadora(String utilizarRegraEntidadeSolucionadora) {
		this.utilizarRegraEntidadeSolucionadora = utilizarRegraEntidadeSolucionadora;
	}

        /**
         *
         * @return
         */
        public ecar.pojo.UsuarioUsu getUsuarioInclusao() {
		return usuarioInclusao;
	}

        /**
         *
         * @param usuarioInclusao
         */
        public void setUsuarioInclusao(ecar.pojo.UsuarioUsu usuarioInclusao) {
		this.usuarioInclusao = usuarioInclusao;
	}
	
    @Override
	public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof VisaoDemandasVisDem) ) return false;
        VisaoDemandasVisDem castOther = (VisaoDemandasVisDem) other;
        return new EqualsBuilder()
            .append(this.getCodVisao(), castOther.getCodVisao())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(this.getCodVisao())
            .toHashCode();
    }

    /**
     *
     * @return
     */
    public Set<VisaoDemandasGrpAcesso> getVisoesGrpAcesso() {
		return visoesGrpAcesso;
	}

    /**
     *
     * @param visoesGrpAcesso
     */
    public void setVisoesGrpAcesso(Set<VisaoDemandasGrpAcesso> visoesGrpAcesso) {
		this.visoesGrpAcesso = visoesGrpAcesso;
	}
	
        /**
         *
         * @return
         */
        public String getEhPermitidoIncluirAnexoDemanda() {
		return ehPermitidoIncluirAnexoDemanda;
	}

        /**
         *
         * @param ehPermitidoIncluirAnexoDemanda
         */
        public void setEhPermitidoIncluirAnexoDemanda(String ehPermitidoIncluirAnexoDemanda) {
		this.ehPermitidoIncluirAnexoDemanda = ehPermitidoIncluirAnexoDemanda;
	}

        /**
         *
         * @return
         */
        public String getEhPermitidoAlterarAnexoDemanda() {
		return ehPermitidoAlterarAnexoDemanda;
	}

        /**
         *
         * @param ehPermitidoAlterarAnexoDemanda
         */
        public void setEhPermitidoAlterarAnexoDemanda(String ehPermitidoAlterarAnexoDemanda) {
		this.ehPermitidoAlterarAnexoDemanda = ehPermitidoAlterarAnexoDemanda;
	}

        /**
         *
         * @return
         */
        public String getEhPermitidoExcluirAnexoDemanda() {
		return ehPermitidoExcluirAnexoDemanda;
	}

        /**
         *
         * @param ehPermitidoExcluirAnexoDemanda
         */
        public void setEhPermitidoExcluirAnexoDemanda(String ehPermitidoExcluirAnexoDemanda) {
		this.ehPermitidoExcluirAnexoDemanda = ehPermitidoExcluirAnexoDemanda;
	}



		public void setVisaoSituacaoDemandas(Set<VisaoSituacaoDemanda> visaoSituacaoDemanda) {
			visaoSituacaoDemandas = visaoSituacaoDemanda;
		}

		public Set<VisaoSituacaoDemanda> getVisaoSituacaoDemandas() {
			return visaoSituacaoDemandas;
		}


}
