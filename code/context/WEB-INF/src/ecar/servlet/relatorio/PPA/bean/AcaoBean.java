package ecar.servlet.relatorio.PPA.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Classe pojo que representa a Fonte de dados de Item de Acao para o relatorio PPA Projeto de Lei
 * @author Gabriel I Solana
 * @since 07/2007
 * 
 */
public class AcaoBean implements Serializable {



	private static final long serialVersionUID = -6652941112311906915L;

	/**
	 * Codigo do Programa <item.getSiglaIett()>
	 */
	private String codigo;
	
	/**
	 * Nome do Programa <item.getNomeIett()>
	 */
	private String nome;	
	
	/**
	 * Orgao Executor
	 */
	private String orgao;

	private String finalidade;
	
	private String descricao;
	
	// espaco em branco
	//private final String SPACE = "";
	
	// tabela PPA
	private BigDecimal valor1;
	private BigDecimal valor2;
	private BigDecimal valor3;
	private BigDecimal valor4;
	private BigDecimal valor5;
	private BigDecimal valor6;
	private BigDecimal valor7;
	private BigDecimal valor8;
	private BigDecimal valor9;
	private BigDecimal valor10;
	private BigDecimal valor11;
	private BigDecimal valor12;
	private BigDecimal valor13;
	private BigDecimal valor14;
	private BigDecimal valor15;
	private BigDecimal valor16;
	private BigDecimal valor17;
	private BigDecimal valor18;
	private BigDecimal valor19;
	private BigDecimal valor20;
	private BigDecimal valor21;
	private BigDecimal valor22;
	private BigDecimal valor23;
	private BigDecimal valor24;
	
	private BigDecimal totalCorrenteVlr1;
	private BigDecimal totalCorrenteVlr2;
	private BigDecimal totalCorrenteVlr3;
	private BigDecimal totalCorrenteVlr4;
	
	private BigDecimal totalCorrenteRecurso1;
	private BigDecimal totalCorrenteRecurso2;
	private BigDecimal totalCorrenteRecurso3;	
	private BigDecimal totalCorrenteRecursoTot;
	
	private BigDecimal totalCapitalVlr1;
	private BigDecimal totalCapitalVlr2;
	private BigDecimal totalCapitalVlr3;
	private BigDecimal totalCapitalVlr4;	
	
	private BigDecimal totalCapitalRecurso1;
	private BigDecimal totalCapitalRecurso2;
	private BigDecimal totalCapitalRecurso3;
	private BigDecimal totalCapitalRecursoTot;
	
	private BigDecimal totalGeralRecurso1;
	private BigDecimal totalGeralRecurso2;
	private BigDecimal totalGeralRecurso3;
	private BigDecimal totalGeralRecursoTot;	
	
	private Set produtos;	


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
        public String getFinalidade() {
		return finalidade;
	}
        /**
         *
         * @param finalidade
         */
        public void setFinalidade(String finalidade) {
		this.finalidade = finalidade;
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
        public String getOrgao() {
		return orgao;
	}
        /**
         *
         * @param orgao
         */
        public void setOrgao(String orgao) {
		this.orgao = orgao;
	}
        /**
         *
         * @return
         */
        public Set getProdutos() {
		return produtos;
	}
        /**
         *
         * @param produtos
         */
        public void setProdutos(Set produtos) {
		this.produtos = produtos;
	}
        /**
         *
         * @return
         */
        public BigDecimal getTotalCapitalRecurso1() {
		return totalCapitalRecurso1==null?new BigDecimal(0):totalCapitalRecurso1;
	}
        /**
         *
         * @return
         */
        public BigDecimal getTotalCapitalRecurso2() {
		return totalCapitalRecurso2==null?new BigDecimal(0):totalCapitalRecurso2;
	}
        /**
         *
         * @return
         */
        public BigDecimal getTotalCapitalRecurso3() {
		return totalCapitalRecurso3==null?new BigDecimal(0):totalCapitalRecurso3;
	}
        /**
         *
         * @return
         */
        public BigDecimal getTotalCapitalVlr1() {
		return totalCapitalVlr1==null?new BigDecimal(0):totalCapitalVlr1;
	}
        /**
         *
         * @return
         */
        public BigDecimal getTotalCapitalVlr2() {
		return totalCapitalVlr2==null?new BigDecimal(0):totalCapitalVlr2;
	}
        /**
         *
         * @return
         */
        public BigDecimal getTotalCapitalVlr3() {
		return totalCapitalVlr3==null?new BigDecimal(0):totalCapitalVlr3;
	}
        /**
         *
         * @return
         */
        public BigDecimal getTotalCapitalVlr4() {
		return totalCapitalVlr4==null?new BigDecimal(0):totalCapitalVlr4;
	}
        /**
         *
         * @return
         */
        public BigDecimal getTotalCorrenteRecurso1() {
		return totalCorrenteRecurso1==null?new BigDecimal(0):totalCorrenteRecurso1;
	}
        /**
         *
         * @return
         */
        public BigDecimal getTotalCorrenteRecurso2() {
		return totalCorrenteRecurso2==null?new BigDecimal(0):totalCorrenteRecurso2;
	}
        /**
         *
         * @return
         */
        public BigDecimal getTotalCorrenteRecurso3() {
		return totalCorrenteRecurso3==null?new BigDecimal(0):totalCorrenteRecurso3;
	}
        /**
         *
         * @return
         */
        public BigDecimal getTotalCorrenteVlr1() {
		return totalCorrenteVlr1==null?new BigDecimal(0):totalCorrenteVlr1;
	}
        /**
         *
         * @return
         */
        public BigDecimal getTotalCorrenteVlr2() {
		return totalCorrenteVlr2==null?new BigDecimal(0):totalCorrenteVlr2;
	}
        /**
         *
         * @return
         */
        public BigDecimal getTotalCorrenteVlr3() {
		return totalCorrenteVlr3==null?new BigDecimal(0):totalCorrenteVlr3;
	}
        /**
         *
         * @return
         */
        public BigDecimal getTotalCorrenteVlr4() {
		return totalCorrenteVlr4==null?new BigDecimal(0):totalCorrenteVlr4;
	}
        /**
         *
         * @return
         */
        public BigDecimal getTotalGeralRecurso1() {
		return totalGeralRecurso1==null?new BigDecimal(0):totalGeralRecurso1;
	}
        /**
         *
         * @return
         */
        public BigDecimal getTotalGeralRecurso2() {
		return totalGeralRecurso2==null?new BigDecimal(0):totalGeralRecurso2;
	}
        /**
         *
         * @return
         */
        public BigDecimal getTotalGeralRecurso3() {
		return totalGeralRecurso3==null?new BigDecimal(0):totalGeralRecurso3;
	}
        /**
         *
         * @return
         */
        public BigDecimal getValor1() {
		return valor1==null?new BigDecimal(0):valor1;
	}
        /**
         *
         * @return
         */
        public BigDecimal getValor10() {
		return valor10==null?new BigDecimal(0):valor10;
	}
        /**
         *
         * @return
         */
        public BigDecimal getValor11() {
		return valor11==null?new BigDecimal(0):valor11;
	}
        /**
         *
         * @return
         */
        public BigDecimal getValor12() {
		return valor12==null?new BigDecimal(0):valor12;
	}
        /**
         *
         * @return
         */
        public BigDecimal getValor13() {
		return valor13==null?new BigDecimal(0):valor13;
	}
        /**
         *
         * @return
         */
        public BigDecimal getValor14() {
		return valor14==null?new BigDecimal(0):valor14;
	}
        /**
         *
         * @return
         */
        public BigDecimal getValor15() {
		return valor15==null?new BigDecimal(0):valor15;
	}
        /**
         *
         * @return
         */
        public BigDecimal getValor16() {
		return valor16==null?new BigDecimal(0):valor16;
	}
        /**
         *
         * @return
         */
        public BigDecimal getValor17() {
		return valor17==null?new BigDecimal(0):valor17;
	}
        /**
         *
         * @return
         */
        public BigDecimal getValor18() {
		return valor18==null?new BigDecimal(0):valor18;
	}
        /**
         *
         * @return
         */
        public BigDecimal getValor19() {
		return valor19==null?new BigDecimal(0):valor19;
	}
        /**
         *
         * @return
         */
        public BigDecimal getValor2() {
		return valor2==null?new BigDecimal(0):valor2;
	}
        /**
         *
         * @return
         */
        public BigDecimal getValor20() {
		return valor20==null?new BigDecimal(0):valor20;
	}
        /**
         *
         * @return
         */
        public BigDecimal getValor21() {
		return valor21==null?new BigDecimal(0):valor21;
	}
        /**
         *
         * @return
         */
        public BigDecimal getValor22() {
		return valor22==null?new BigDecimal(0):valor22;
	}
        /**
         *
         * @return
         */
        public BigDecimal getValor23() {
		return valor23==null?new BigDecimal(0):valor23;
	}
        /**
         *
         * @return
         */
        public BigDecimal getValor24() {
		return valor24==null?new BigDecimal(0):valor24;
	}
        /**
         *
         * @return
         */
        public BigDecimal getValor3() {
		return valor3==null?new BigDecimal(0):valor3;
	}
        /**
         *
         * @return
         */
        public BigDecimal getValor4() {
		return valor4==null?new BigDecimal(0):valor4;
	}
        /**
         *
         * @return
         */
        public BigDecimal getValor5() {
		return valor5==null?new BigDecimal(0):valor5;
	}
        /**
         *
         * @return
         */
        public BigDecimal getValor6() {
		return valor6==null?new BigDecimal(0):valor6;
	}
        /**
         *
         * @return
         */
        public BigDecimal getValor7() {
		return valor7==null?new BigDecimal(0):valor7;
	}
        /**
         *
         * @return
         */
        public BigDecimal getValor8() {
		return valor8==null?new BigDecimal(0):valor8;
	}
        /**
         *
         * @return
         */
        public BigDecimal getValor9() {
		return valor9==null?new BigDecimal(0):valor9;
	}
        /**
         *
         * @return
         */
        public BigDecimal getTotalCapitalRecursoTot() {
		return totalCapitalRecursoTot==null?new BigDecimal(0):totalCapitalRecursoTot;
	}
        /**
         *
         * @return
         */
        public BigDecimal getTotalCorrenteRecursoTot() {
		return totalCorrenteRecursoTot==null?new BigDecimal(0):totalCorrenteRecursoTot;
	}
        /**
         *
         * @return
         */
        public BigDecimal getTotalGeralRecursoTot() {
		return totalGeralRecursoTot==null?new BigDecimal(0):totalGeralRecursoTot;
	}
        /**
         *
         * @param totalCapitalRecurso1
         */
        public void setTotalCapitalRecurso1(BigDecimal totalCapitalRecurso1) {
		this.totalCapitalRecurso1 = totalCapitalRecurso1;
	}
        /**
         *
         * @param totalCapitalRecurso2
         */
        public void setTotalCapitalRecurso2(BigDecimal totalCapitalRecurso2) {
		this.totalCapitalRecurso2 = totalCapitalRecurso2;
	}
        /**
         *
         * @param totalCapitalRecurso3
         */
        public void setTotalCapitalRecurso3(BigDecimal totalCapitalRecurso3) {
		this.totalCapitalRecurso3 = totalCapitalRecurso3;
	}
        /**
         *
         * @param totalCapitalVlr1
         */
        public void setTotalCapitalVlr1(BigDecimal totalCapitalVlr1) {
		this.totalCapitalVlr1 = totalCapitalVlr1;
	}
        /**
         *
         * @param totalCapitalVlr2
         */
        public void setTotalCapitalVlr2(BigDecimal totalCapitalVlr2) {
		this.totalCapitalVlr2 = totalCapitalVlr2;
	}
        /**
         *
         * @param totalCapitalVlr3
         */
        public void setTotalCapitalVlr3(BigDecimal totalCapitalVlr3) {
		this.totalCapitalVlr3 = totalCapitalVlr3;
	}
        /**
         *
         * @param totalCapitalVlr4
         */
        public void setTotalCapitalVlr4(BigDecimal totalCapitalVlr4) {
		this.totalCapitalVlr4 = totalCapitalVlr4;
	}
        /**
         *
         * @param totalCorrenteRecurso1
         */
        public void setTotalCorrenteRecurso1(BigDecimal totalCorrenteRecurso1) {
		this.totalCorrenteRecurso1 = totalCorrenteRecurso1;
	}
        /**
         *
         * @param totalCorrenteRecurso2
         */
        public void setTotalCorrenteRecurso2(BigDecimal totalCorrenteRecurso2) {
		this.totalCorrenteRecurso2 = totalCorrenteRecurso2;
	}
        /**
         *
         * @param totalCorrenteRecurso3
         */
        public void setTotalCorrenteRecurso3(BigDecimal totalCorrenteRecurso3) {
		this.totalCorrenteRecurso3 = totalCorrenteRecurso3;
	}
        /**
         *
         * @param totalCorrenteVlr1
         */
        public void setTotalCorrenteVlr1(BigDecimal totalCorrenteVlr1) {
		this.totalCorrenteVlr1 = totalCorrenteVlr1;
	}
        /**
         *
         * @param totalCorrenteVlr2
         */
        public void setTotalCorrenteVlr2(BigDecimal totalCorrenteVlr2) {
		this.totalCorrenteVlr2 = totalCorrenteVlr2;
	}
        /**
         *
         * @param totalCorrenteVlr3
         */
        public void setTotalCorrenteVlr3(BigDecimal totalCorrenteVlr3) {
		this.totalCorrenteVlr3 = totalCorrenteVlr3;
	}
        /**
         *
         * @param totalCorrenteVlr4
         */
        public void setTotalCorrenteVlr4(BigDecimal totalCorrenteVlr4) {
		this.totalCorrenteVlr4 = totalCorrenteVlr4;
	}
        /**
         *
         * @param totalGeralRecurso1
         */
        public void setTotalGeralRecurso1(BigDecimal totalGeralRecurso1) {
		this.totalGeralRecurso1 = totalGeralRecurso1;
	}
        /**
         *
         * @param totalGeralRecurso2
         */
        public void setTotalGeralRecurso2(BigDecimal totalGeralRecurso2) {
		this.totalGeralRecurso2 = totalGeralRecurso2;
	}
        /**
         *
         * @param totalGeralRecurso3
         */
        public void setTotalGeralRecurso3(BigDecimal totalGeralRecurso3) {
		this.totalGeralRecurso3 = totalGeralRecurso3;
	}
        /**
         *
         * @param valor1
         */
        public void setValor1(BigDecimal valor1) {
		this.valor1 = valor1;
	}
        /**
         *
         * @param valor10
         */
        public void setValor10(BigDecimal valor10) {
		this.valor10 = valor10;
	}
        /**
         *
         * @param valor11
         */
        public void setValor11(BigDecimal valor11) {
		this.valor11 = valor11;
	}
        /**
         *
         * @param valor12
         */
        public void setValor12(BigDecimal valor12) {
		this.valor12 = valor12;
	}
        /**
         *
         * @param valor13
         */
        public void setValor13(BigDecimal valor13) {
		this.valor13 = valor13;
	}
        /**
         *
         * @param valor14
         */
        public void setValor14(BigDecimal valor14) {
		this.valor14 = valor14;
	}
        /**
         *
         * @param valor15
         */
        public void setValor15(BigDecimal valor15) {
		this.valor15 = valor15;
	}
        /**
         *
         * @param valor16
         */
        public void setValor16(BigDecimal valor16) {
		this.valor16 = valor16;
	}
        /**
         *
         * @param valor17
         */
        public void setValor17(BigDecimal valor17) {
		this.valor17 = valor17;
	}
        /**
         *
         * @param valor18
         */
        public void setValor18(BigDecimal valor18) {
		this.valor18 = valor18;
	}
        /**
         *
         * @param valor19
         */
        public void setValor19(BigDecimal valor19) {
		this.valor19 = valor19;
	}
        /**
         *
         * @param valor2
         */
        public void setValor2(BigDecimal valor2) {
		this.valor2 = valor2;
	}
        /**
         *
         * @param valor20
         */
        public void setValor20(BigDecimal valor20) {
		this.valor20 = valor20;
	}
        /**
         *
         * @param valor21
         */
        public void setValor21(BigDecimal valor21) {
		this.valor21 = valor21;
	}
        /**
         *
         * @param valor22
         */
        public void setValor22(BigDecimal valor22) {
		this.valor22 = valor22;
	}
        /**
         *
         * @param valor23
         */
        public void setValor23(BigDecimal valor23) {
		this.valor23 = valor23;
	}
        /**
         *
         * @param valor24
         */
        public void setValor24(BigDecimal valor24) {
		this.valor24 = valor24;
	}
        /**
         *
         * @param valor3
         */
        public void setValor3(BigDecimal valor3) {
		this.valor3 = valor3;
	}
        /**
         *
         * @param valor4
         */
        public void setValor4(BigDecimal valor4) {
		this.valor4 = valor4;
	}
        /**
         *
         * @param valor5
         */
        public void setValor5(BigDecimal valor5) {
		this.valor5 = valor5;
	}
        /**
         *
         * @param valor6
         */
        public void setValor6(BigDecimal valor6) {
		this.valor6 = valor6;
	}
        /**
         *
         * @param valor7
         */
        public void setValor7(BigDecimal valor7) {
		this.valor7 = valor7;
	}
        /**
         *
         * @param valor8
         */
        public void setValor8(BigDecimal valor8) {
		this.valor8 = valor8;
	}
        /**
         *
         * @param valor9
         */
        public void setValor9(BigDecimal valor9) {
		this.valor9 = valor9;
	}
        /**
         *
         * @param totalCapitalRecursoTot
         */
        public void setTotalCapitalRecursoTot(BigDecimal totalCapitalRecursoTot) {
		this.totalCapitalRecursoTot = totalCapitalRecursoTot;
	}
        /**
         *
         * @param totalCorrenteRecursoTot
         */
        public void setTotalCorrenteRecursoTot(BigDecimal totalCorrenteRecursoTot) {
		this.totalCorrenteRecursoTot = totalCorrenteRecursoTot;
	}
        /**
         *
         * @param totalGeralRecursoTot
         */
        public void setTotalGeralRecursoTot(BigDecimal totalGeralRecursoTot) {
		this.totalGeralRecursoTot = totalGeralRecursoTot;
	}


	
	



	
	
	
}
