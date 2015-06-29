package ecar.servlet.relatorio.PPA.bean;

import java.io.Serializable;

/**
 *
 * @author 70744416353
 */
public class ProdutoBean implements Serializable {


	private static final long serialVersionUID = 7965930996726766014L;

	private String descricao;
	private String produto;
	private Double centroExpandido;
	private Double leste;
	private Double norte;
	private Double noroeste;
	private Double oeste;
	private Double sudoeste;
	private Double estado;
	private Double total;
	private String unidMedida;
	private Integer indice;
	
	
        /**
         *
         * @return
         */
        public Integer getIndice() {
		return indice;
	}
        /**
         *
         * @param indice
         */
        public void setIndice(Integer indice) {
		this.indice = indice;
	}
        /**
         *
         * @return
         */
        public Double getCentroExpandido() {
		return centroExpandido;
	}
        /**
         *
         * @param centroExpandido
         */
        public void setCentroExpandido(Double centroExpandido) {
		this.centroExpandido = centroExpandido;
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
        public Double getEstado() {
		return estado;
	}
        /**
         *
         * @param estado
         */
        public void setEstado(Double estado) {
		this.estado = estado;
	}
        /**
         *
         * @return
         */
        public Double getLeste() {
		return leste;
	}
        /**
         *
         * @param leste
         */
        public void setLeste(Double leste) {
		this.leste = leste;
	}
        /**
         *
         * @return
         */
        public Double getNoroeste() {
		return noroeste;
	}
        /**
         *
         * @param noroeste
         */
        public void setNoroeste(Double noroeste) {
		this.noroeste = noroeste;
	}
        /**
         *
         * @return
         */
        public Double getNorte() {
		return norte;
	}
        /**
         *
         * @param norte
         */
        public void setNorte(Double norte) {
		this.norte = norte;
	}
        /**
         *
         * @return
         */
        public Double getOeste() {
		return oeste;
	}
        /**
         *
         * @param oeste
         */
        public void setOeste(Double oeste) {
		this.oeste = oeste;
	}
        /**
         *
         * @return
         */
        public String getProduto() {
		return produto;
	}
        /**
         *
         * @param produto
         */
        public void setProduto(String produto) {
		this.produto = produto;
	}
        /**
         *
         * @return
         */
        public Double getSudoeste() {
		return sudoeste;
	}
        /**
         *
         * @param sudoeste
         */
        public void setSudoeste(Double sudoeste) {
		this.sudoeste = sudoeste;
	}
        /**
         *
         * @return
         */
        public Double getTotal() {
		return total;
	}
        /**
         *
         * @param total
         */
        public void setTotal(Double total) {
		this.total = total;
	}
        /**
         *
         * @return
         */
        public String getUnidMedida() {
		return unidMedida;
	}
        /**
         *
         * @param unidMedida
         */
        public void setUnidMedida(String unidMedida) {
		this.unidMedida = unidMedida;
	}
	
	
	
}
