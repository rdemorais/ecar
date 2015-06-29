package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author 70744416353
 */
public class HistoricoMotivo implements Serializable {

	private static final long serialVersionUID = -240022099637012929L;

	/** cod_moh - Chave Primaria (PK) */
    private Long codMoh;

    /** cod_gmh - Chave estrangeira (FK) - Tabela <tb_historico_grupo_motivo_gmh> */
    private HistoricoGrupoMotivo historicoGrupoMotivo;
    
    /** descricao */
    private String descricao;
    
    /** sigla_tabela_afetada */
    private String siglaTabelaAfetada;
    
    /** cod_acao_moh */
    private Integer codAcaoMoh;
    
    /** HistoricoMaster */
    private Set historicoMasters;
    

    /**
     *
     * @return
     */
    public Set getHistoricoMasters() {
		return historicoMasters;
	}


    /**
     *
     * @param historicoMasters
     */
    public void setHistoricoMasters(Set historicoMasters) {
		this.historicoMasters = historicoMasters;
	}


    /**
     *
     */
    public HistoricoMotivo() {
	}


        /**
         *
         * @return
         */
        public Integer getCodAcaoMoh() {
		return codAcaoMoh;
	}


        /**
         *
         * @param codAcaoMoh
         */
        public void setCodAcaoMoh(Integer codAcaoMoh) {
		this.codAcaoMoh = codAcaoMoh;
	}


        /**
         *
         * @return
         */
        public Long getCodMoh() {
		return codMoh;
	}


        /**
         *
         * @param codMoh
         */
        public void setCodMoh(Long codMoh) {
		this.codMoh = codMoh;
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
         *
         * @return
         */
        public HistoricoGrupoMotivo getHistoricoGrupoMotivo() {
		return historicoGrupoMotivo;
	}


        /**
         *
         * @param historicoGrupoMotivo
         */
        public void setHistoricoGrupoMotivo(HistoricoGrupoMotivo historicoGrupoMotivo) {
		this.historicoGrupoMotivo = historicoGrupoMotivo;
	}


        /**
         *
         * @return
         */
        public String getSiglaTabelaAfetada() {
		return siglaTabelaAfetada;
	}


        /**
         *
         * @param siglaTabelaAfetada
         */
        public void setSiglaTabelaAfetada(String siglaTabelaAfetada) {
		this.siglaTabelaAfetada = siglaTabelaAfetada;
	}
		
    
    
}
