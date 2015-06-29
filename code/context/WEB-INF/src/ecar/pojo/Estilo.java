package ecar.pojo;

import java.io.Serializable;

/**
 *
 * @author 70744416353
 */
public class Estilo implements Serializable {

	private static final long serialVersionUID = 1128105977946214930L;

	private Integer codEstilo;
	private String nome;
	private String descricao;

        /**
         *
         * @param pCodEstilo
         * @param pNome
         * @param pDescricao
         */
        public Estilo(String pCodEstilo, String pNome, String pDescricao) {
		this.nome = pNome;
		this.descricao = pDescricao;
	}
	
        /**
         *
         */
        public Estilo() {
		
	}
	
        /**
         *
         * @return
         */
        public Integer getCodEstilo() {
		return codEstilo;
	}
        /**
         *
         * @param codEstilo
         */
        public void setCodEstilo(Integer codEstilo) {
		this.codEstilo = codEstilo;
	}
        /**
         *
         * @return
         */
        public String getNome() {
		return nome;
	}
        /**
         *
         * @param nome
         */
        public void setNome(String nome) {
		this.nome = nome;
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
