package ecar.pojo;

import java.io.Serializable;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import comum.util.Pagina;

import ecar.exception.ECARException;

/**
 *
 * @author 70744416353
 */
public class RegDemandaAnexoRegdan implements Serializable {

	private static final long serialVersionUID = -2481032929186535170L;

    private Long cod;

    private String srcAnexo;

    private String descricao;
    
    private RegDemandaRegd regDemanda;

    private Date dataInclusao;
    
    /**
     *
     */
    public RegDemandaAnexoRegdan() {
    	super();
    }

    /**
     *
     * @return
     */
    public Long getCod() {
		return cod;
	}

        /**
         *
         * @param cod
         */
        public void setCod(Long cod) {
		this.cod = cod;
	}

        /**
         *
         * @return
         */
        public String getSrcAnexo() {
		return srcAnexo;
	}

        /**
         *
         * @param srcAnexo
         */
        public void setSrcAnexo(String srcAnexo) {
		this.srcAnexo = srcAnexo;
	}

        /**
         *
         * @return
         */
        public RegDemandaRegd getRegDemanda() {
		return regDemanda;
	}

        /**
         *
         * @param regDemanda
         */
        public void setRegDemanda(RegDemandaRegd regDemanda) {
		this.regDemanda = regDemanda;
	}

        /**
         *
         * @return
         */
        public Date getDataInclusao() {
		return dataInclusao;
	}

        /**
         *
         * @param dataInclusao
         */
        public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

        /**
         *
         * @return
         */
        public String getDescricao() {
		return descricao;
	}

        /**
         *
         * @param descricao
         */
        public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	

	/**
	 * Método que mapeia objeto do Formulario em objeto negócio
	 * @param request
         * @param registroDemandaAnexo
	 * @throws ECARException
	 */
	public static void mapearObjetoNegocio(HttpServletRequest request, RegDemandaAnexoRegdan registroDemandaAnexo) throws ECARException {

		registroDemandaAnexo.setDescricao(Pagina.getParamStr(request, "descricaoAnexoDemanda"));
		registroDemandaAnexo.setDataInclusao(new Date(System.currentTimeMillis()));
		
	}
		
    
}
