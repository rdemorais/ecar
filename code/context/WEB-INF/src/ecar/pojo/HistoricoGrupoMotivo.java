package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

/** 
 * Classe responsavel para armazenar dados da tabela tb_historico_grupo_motivo_gmh
 * - Modificado para comportar historico
 * 
 * @author gabriel
 * @since 0.1, 24/05/2007
 * @version 0.1, 24/05/2007
 */
public class HistoricoGrupoMotivo implements Serializable {

	private static final long serialVersionUID = -7764928825820232229L;

	/** cod_gmh - Chave Primaria (PK) */
	private Long codGMH;
	
	/** descricao */
	private String descricao;

	/** HistoricoMotivo */
	private Set historicoMotivos;
	

        /**
         *
         * @return
         */
        public Set getHistoricoMotivos() {
		return historicoMotivos;
	}

        /**
         *
         * @param historicoMotivos
         */
        public void setHistoricoMotivos(Set historicoMotivos) {
		this.historicoMotivos = historicoMotivos;
	}

        /**
         *
         * @return
         */
        public Long getCodGMH() {
		return codGMH;
	}

        /**
         *
         * @param codGMH
         */
        public void setCodGMH(Long codGMH) {
		this.codGMH = codGMH;
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
	
	
	
}
