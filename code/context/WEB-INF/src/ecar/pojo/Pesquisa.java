package ecar.pojo;

import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author 70744416353
 */
public class Pesquisa implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 959730070579782434L;
	long codPesquisa; 
	private String nomePesquisa;
	private String indTipoPesquisa; // Pesquisa de Sistema ou de Usuário 
	private String indTipoReferencia; // Última, Atual e Exibida
	private int periodo;
	private UsuarioUsu usuarioUsu; // Usuário de adição
	private TipoAcompanhamentoTa tipoAcompanhamentoTa;
	private Set pesquisaIetts;
	private Set sisAtributoSatbs;
/*
	
	// TO DO: colocar grupo de acesso
*/
	
        /**
         *
         * @return
         */
        public long getCodPesquisa() {
		return codPesquisa;
	}
        /**
         *
         * @param codPesquisa
         */
        public void setCodPesquisa(long codPesquisa) {
		this.codPesquisa = codPesquisa;
	}
        /**
         *
         * @return
         */
        public String getNomePesquisa() {
		return nomePesquisa;
	}
        /**
         *
         * @param nomePesquisa
         */
        public void setNomePesquisa(String nomePesquisa) {
		this.nomePesquisa = nomePesquisa;
	}
        /**
         *
         * @return
         */
        public String getIndTipoPesquisa() {
		return indTipoPesquisa;
	}
        /**
         *
         * @param indTipoPesquisa
         */
        public void setIndTipoPesquisa(String indTipoPesquisa) {
		this.indTipoPesquisa = indTipoPesquisa;
	}

        /**
         *
         * @return
         */
        public int getPeriodo() {
		return periodo;
	}
        /**
         *
         * @param periodo
         */
        public void setPeriodo(int periodo) {
		this.periodo = periodo;
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
        public TipoAcompanhamentoTa getTipoAcompanhamentoTa() {
		return tipoAcompanhamentoTa;
	}
        /**
         *
         * @param tipoAcompanhamentoTa
         */
        public void setTipoAcompanhamentoTa(TipoAcompanhamentoTa tipoAcompanhamentoTa) {
		this.tipoAcompanhamentoTa = tipoAcompanhamentoTa;
	}

        /**
         *
         * @return
         */
        public Set getPesquisaIetts() {
		return pesquisaIetts;
	}
        /**
         *
         * @param pesquisaIetts
         */
        public void setPesquisaIetts(Set pesquisaIetts) {
		this.pesquisaIetts = pesquisaIetts;
	}
	
        /**
         *
         * @return
         */
        public String getIndTipoReferencia() {
		return indTipoReferencia;
	}
        /**
         *
         * @param indTipoReferencia
         */
        public void setIndTipoReferencia(String indTipoReferencia) {
		this.indTipoReferencia = indTipoReferencia;
	}
        /**
         *
         * @return
         */
        public Set getSisAtributoSatbs() {
		return sisAtributoSatbs;
	}
        /**
         *
         * @param sisAtributoSatbs
         */
        public void setSisAtributoSatbs(Set sisAtributoSatbs) {
		this.sisAtributoSatbs = sisAtributoSatbs;
	}
	
}

