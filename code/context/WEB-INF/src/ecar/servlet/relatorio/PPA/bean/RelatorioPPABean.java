package ecar.servlet.relatorio.PPA.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe pojo que representa a Fonte de dados de Relatorio PPA - Projeto de Lei
 * @author Gabriel I Solana
 * @since 08/2007
 * 
 */
public class RelatorioPPABean implements Serializable {

	private static final long serialVersionUID = 2415324680674059639L;

	/**
	 * Codigo da Linha de acao <item.getSiglaIett()>
	 */
	private String codigo;
	
	/**
	 * Nome da Linha de acao <item.getNomeIett()>
	 */
	private String nome;
	
	private String descricao;
	
	
	
	private ArrayList<ProgramaBean> programas;

	
        /**
         *
         * @return
         */
        public String getCodigo() {
		return codigo;
	}

        /**
         *
         * @param codigo
         */
        public void setCodigo(String codigo) {
		this.codigo = codigo;
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
        public ArrayList<ProgramaBean> getProgramas() {
		return programas;
	}

        /**
         *
         * @param programas
         */
        public void setProgramas(ArrayList<ProgramaBean> programas) {
		this.programas = programas;
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
