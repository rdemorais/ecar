package ecar.servlet.relatorio.PPA_Util;

import java.math.BigDecimal;

import ecar.servlet.relatorio.PPA.bean.AcaoBean;
import ecar.servlet.relatorio.PPA.bean.ProgramaBean;
import ecar.servlet.relatorio.PPA_LinhaAcao.PPA_LinhaAcaoBean;
import ecar.servlet.relatorio.PPA_Orgao.PPA_OrgaoBean;
import ecar.servlet.relatorio.PPA_Programa.PPA_ProgramaBean;

/**
 * Classe utilitaria para calcular os totais das tabelas de PPA
 * @author Gabriel I Solana
 * @since 07/2007
 *
 */
public class CalcularTotalVisitor {

    /**
     *
     */
    public CalcularTotalVisitor(){}
	

	
	/**
	 * Calcula totais da tabela de resumo de Itens de Programa para o relatorio PPA 
	 * @param programa
	 */
	public void visit(ProgramaBean programa){
		
		// coluna corrente ano 1
		BigDecimal totalCorrenteVlr1 = new BigDecimal(0);
		totalCorrenteVlr1 = totalCorrenteVlr1.add(programa.getValor13());
		totalCorrenteVlr1 = totalCorrenteVlr1.add(programa.getValor14());
		totalCorrenteVlr1 = totalCorrenteVlr1.add(programa.getValor15());
		programa.setTotalCorrenteVlr1( totalCorrenteVlr1 );
		
		// coluna corrente ano 2
		BigDecimal totalCorrenteVlr2 = new BigDecimal(0);
		totalCorrenteVlr2 = totalCorrenteVlr2.add(programa.getValor16());
		totalCorrenteVlr2 = totalCorrenteVlr2.add(programa.getValor17());
		totalCorrenteVlr2 = totalCorrenteVlr2.add(programa.getValor18());
		programa.setTotalCorrenteVlr2( totalCorrenteVlr2 );
		
		// coluna corrente ano 3
		BigDecimal totalCorrenteVlr3 = new BigDecimal(0);
		totalCorrenteVlr3 = totalCorrenteVlr3.add(programa.getValor19());
		totalCorrenteVlr3 = totalCorrenteVlr3.add(programa.getValor20());
		totalCorrenteVlr3 = totalCorrenteVlr3.add(programa.getValor21());
		programa.setTotalCorrenteVlr3( totalCorrenteVlr3 );
		
		// coluna corrente ano 4
		BigDecimal totalCorrenteVlr4 = new BigDecimal(0);
		totalCorrenteVlr4 = totalCorrenteVlr4.add(programa.getValor22());
		totalCorrenteVlr4 = totalCorrenteVlr4.add(programa.getValor23());
		totalCorrenteVlr4 = totalCorrenteVlr4.add(programa.getValor24());
		programa.setTotalCorrenteVlr4( totalCorrenteVlr4 );		
		
		// linha 1 total ( correntes )
		BigDecimal totalCorrenteRecurso1 = new BigDecimal(0);
		totalCorrenteRecurso1 = totalCorrenteRecurso1.add(programa.getValor15());
		totalCorrenteRecurso1 = totalCorrenteRecurso1.add(programa.getValor18());
		totalCorrenteRecurso1 = totalCorrenteRecurso1.add(programa.getValor21());
		totalCorrenteRecurso1 = totalCorrenteRecurso1.add(programa.getValor24());		
		programa.setTotalCorrenteRecurso1( totalCorrenteRecurso1 );			
		
		//	linha 2 total ( correntes )
		BigDecimal totalCorrenteRecurso2 = new BigDecimal(0);
		totalCorrenteRecurso2 = totalCorrenteRecurso2.add(programa.getValor13());
		totalCorrenteRecurso2 = totalCorrenteRecurso2.add(programa.getValor16());
		totalCorrenteRecurso2 = totalCorrenteRecurso2.add(programa.getValor19());
		totalCorrenteRecurso2 = totalCorrenteRecurso2.add(programa.getValor22());		
		programa.setTotalCorrenteRecurso2( totalCorrenteRecurso2 );
		
		//	linha 3 total ( correntes )
		BigDecimal totalCorrenteRecurso3 = new BigDecimal(0);
		totalCorrenteRecurso3 = totalCorrenteRecurso3.add(programa.getValor14());
		totalCorrenteRecurso3 = totalCorrenteRecurso3.add(programa.getValor17());
		totalCorrenteRecurso3 = totalCorrenteRecurso3.add(programa.getValor20());
		totalCorrenteRecurso3 = totalCorrenteRecurso3.add(programa.getValor23());		
		programa.setTotalCorrenteRecurso3( totalCorrenteRecurso3 );
		
		BigDecimal totalCorrenteRecursoTot = new BigDecimal(0);
		totalCorrenteRecursoTot = totalCorrenteRecursoTot.add( totalCorrenteRecurso1 );
		totalCorrenteRecursoTot = totalCorrenteRecursoTot.add(totalCorrenteRecurso2 );
		totalCorrenteRecursoTot = totalCorrenteRecursoTot.add(totalCorrenteRecurso3 );		
		programa.setTotalCorrenteRecursoTot( totalCorrenteRecursoTot );
		
		// coluna capital ano 1
		BigDecimal totalCapitalVlr1 = new BigDecimal(0);
		totalCapitalVlr1 = totalCapitalVlr1.add(programa.getValor1());
		totalCapitalVlr1 = totalCapitalVlr1.add(programa.getValor2());
		totalCapitalVlr1 = totalCapitalVlr1.add(programa.getValor3());
		programa.setTotalCapitalVlr1( totalCapitalVlr1 );
		
		// coluna capital ano 2
		BigDecimal totalCapitalVlr2 = new BigDecimal(0);
		totalCapitalVlr2 = totalCapitalVlr2.add(programa.getValor4());
		totalCapitalVlr2 = totalCapitalVlr2.add(programa.getValor5());
		totalCapitalVlr2 = totalCapitalVlr2.add(programa.getValor6());
		programa.setTotalCapitalVlr2( totalCapitalVlr2 );		
		
		// coluna capital ano 3
		BigDecimal totalCapitalVlr3 = new BigDecimal(0);
		totalCapitalVlr3 = totalCapitalVlr3.add(programa.getValor7());
		totalCapitalVlr3 = totalCapitalVlr3.add(programa.getValor8());
		totalCapitalVlr3 = totalCapitalVlr3.add(programa.getValor9());
		programa.setTotalCapitalVlr3( totalCapitalVlr3 );		

		// coluna capital ano 4
		BigDecimal totalCapitalVlr4 = new BigDecimal(0);
		totalCapitalVlr4 = totalCapitalVlr4.add(programa.getValor10());
		totalCapitalVlr4 = totalCapitalVlr4.add(programa.getValor11());
		totalCapitalVlr4 = totalCapitalVlr4.add(programa.getValor12());
		programa.setTotalCapitalVlr4( totalCapitalVlr4 );				
		
		// linha 1 total ( capital )
		BigDecimal totalCapitalRecurso1 = new BigDecimal(0);
		totalCapitalRecurso1 = totalCapitalRecurso1.add(programa.getValor3());
		totalCapitalRecurso1 = totalCapitalRecurso1.add(programa.getValor6());
		totalCapitalRecurso1 = totalCapitalRecurso1.add(programa.getValor9());
		totalCapitalRecurso1 = totalCapitalRecurso1.add(programa.getValor12());		
		programa.setTotalCapitalRecurso1( totalCapitalRecurso1 );	

		// linha 2 total ( capital )
		BigDecimal totalCapitalRecurso2 = new BigDecimal(0);
		totalCapitalRecurso2 = totalCapitalRecurso2.add(programa.getValor1());
		totalCapitalRecurso2 = totalCapitalRecurso2.add(programa.getValor4());
		totalCapitalRecurso2 = totalCapitalRecurso2.add(programa.getValor7());
		totalCapitalRecurso2 = totalCapitalRecurso2.add(programa.getValor10());		
		programa.setTotalCapitalRecurso2( totalCapitalRecurso2 );			
		
		// linha 3 total ( capital )
		BigDecimal totalCapitalRecurso3 = new BigDecimal(0);
		totalCapitalRecurso3 = totalCapitalRecurso3.add(programa.getValor2());
		totalCapitalRecurso3 = totalCapitalRecurso3.add(programa.getValor5());
		totalCapitalRecurso3 = totalCapitalRecurso3.add(programa.getValor8());
		totalCapitalRecurso3 = totalCapitalRecurso3.add(programa.getValor11());		
		programa.setTotalCapitalRecurso3( totalCapitalRecurso3 );			
		
		BigDecimal totalCapitalRecursoTot = new BigDecimal(0);
		totalCapitalRecursoTot = totalCapitalRecursoTot.add( totalCapitalRecurso1 );
		totalCapitalRecursoTot = totalCapitalRecursoTot.add( totalCapitalRecurso2 );
		totalCapitalRecursoTot = totalCapitalRecursoTot.add( totalCapitalRecurso3 );		
		programa.setTotalCapitalRecursoTot( totalCapitalRecursoTot );		
		
		
		// coluna total geral - linha 1
		BigDecimal totalGeralRecurso1 = new BigDecimal(0);
		totalGeralRecurso1 = totalGeralRecurso1.add( totalCorrenteRecurso1 );
		totalGeralRecurso1 = totalGeralRecurso1.add( totalCapitalRecurso1 );	
		programa.setTotalGeralRecurso1( totalGeralRecurso1 );
		
		// coluna total geral - linha 2
		BigDecimal totalGeralRecurso2 = new BigDecimal(0);
		totalGeralRecurso2 = totalGeralRecurso2.add( totalCorrenteRecurso2 );
		totalGeralRecurso2 = totalGeralRecurso2.add( totalCapitalRecurso2 );	
		programa.setTotalGeralRecurso2( totalGeralRecurso2 );

		// coluna total geral - linha 3
		BigDecimal totalGeralRecurso3 = new BigDecimal(0);
		totalGeralRecurso3 = totalGeralRecurso3.add( totalCorrenteRecurso3 );
		totalGeralRecurso3 = totalGeralRecurso3.add( totalCapitalRecurso3 );	
		programa.setTotalGeralRecurso3( totalGeralRecurso3 );
		
		BigDecimal totalGeralRecursoTot = new BigDecimal(0);
		totalGeralRecursoTot = totalGeralRecursoTot.add( totalGeralRecurso1 );
		totalGeralRecursoTot = totalGeralRecursoTot.add( totalGeralRecurso2 );
		totalGeralRecursoTot = totalGeralRecursoTot.add( totalGeralRecurso3 );		
		programa.setTotalGeralRecursoTot( totalGeralRecursoTot );	
		
		
	}	
	
	/**
	 * Calcula totais da tabela de resumo de itens de Acoes
	 * @param acao
	 */
	public void visit(AcaoBean acao){
		
		// coluna corrente ano 1
		BigDecimal totalCorrenteVlr1 = new BigDecimal(0);
		totalCorrenteVlr1 = totalCorrenteVlr1.add(acao.getValor13());
		totalCorrenteVlr1 = totalCorrenteVlr1.add(acao.getValor14());
		totalCorrenteVlr1 = totalCorrenteVlr1.add(acao.getValor15());
		acao.setTotalCorrenteVlr1( totalCorrenteVlr1 );
		
		// coluna corrente ano 2
		BigDecimal totalCorrenteVlr2 = new BigDecimal(0);
		totalCorrenteVlr2 = totalCorrenteVlr2.add(acao.getValor16());
		totalCorrenteVlr2 = totalCorrenteVlr2.add(acao.getValor17());
		totalCorrenteVlr2 = totalCorrenteVlr2.add(acao.getValor18());
		acao.setTotalCorrenteVlr2( totalCorrenteVlr2 );
		
		// coluna corrente ano 3
		BigDecimal totalCorrenteVlr3 = new BigDecimal(0);
		totalCorrenteVlr3 = totalCorrenteVlr3.add(acao.getValor19());
		totalCorrenteVlr3 = totalCorrenteVlr3.add(acao.getValor20());
		totalCorrenteVlr3 = totalCorrenteVlr3.add(acao.getValor21());
		acao.setTotalCorrenteVlr3( totalCorrenteVlr3 );
		
		// coluna corrente ano 4
		BigDecimal totalCorrenteVlr4 = new BigDecimal(0);
		totalCorrenteVlr4 = totalCorrenteVlr4.add(acao.getValor22());
		totalCorrenteVlr4 = totalCorrenteVlr4.add(acao.getValor23());
		totalCorrenteVlr4 = totalCorrenteVlr4.add(acao.getValor24());
		acao.setTotalCorrenteVlr4( totalCorrenteVlr4 );		
		
		// linha 1 total ( correntes )
		BigDecimal totalCorrenteRecurso1 = new BigDecimal(0);
		totalCorrenteRecurso1 = totalCorrenteRecurso1.add(acao.getValor15());
		totalCorrenteRecurso1 = totalCorrenteRecurso1.add(acao.getValor18());
		totalCorrenteRecurso1 = totalCorrenteRecurso1.add(acao.getValor21());
		totalCorrenteRecurso1 = totalCorrenteRecurso1.add(acao.getValor24());		
		acao.setTotalCorrenteRecurso1( totalCorrenteRecurso1 );			
		
		//	linha 2 total ( correntes )
		BigDecimal totalCorrenteRecurso2 = new BigDecimal(0);
		totalCorrenteRecurso2 = totalCorrenteRecurso2.add(acao.getValor13());
		totalCorrenteRecurso2 = totalCorrenteRecurso2.add(acao.getValor16());
		totalCorrenteRecurso2 = totalCorrenteRecurso2.add(acao.getValor19());
		totalCorrenteRecurso2 = totalCorrenteRecurso2.add(acao.getValor22());		
		acao.setTotalCorrenteRecurso2( totalCorrenteRecurso2 );
		
		//	linha 3 total ( correntes )
		BigDecimal totalCorrenteRecurso3 = new BigDecimal(0);
		totalCorrenteRecurso3 = totalCorrenteRecurso3.add(acao.getValor14());
		totalCorrenteRecurso3 = totalCorrenteRecurso3.add(acao.getValor17());
		totalCorrenteRecurso3 = totalCorrenteRecurso3.add(acao.getValor20());
		totalCorrenteRecurso3 = totalCorrenteRecurso3.add(acao.getValor23());		
		acao.setTotalCorrenteRecurso3( totalCorrenteRecurso3 );
		
		BigDecimal totalCorrenteRecursoTot = new BigDecimal(0);
		totalCorrenteRecursoTot = totalCorrenteRecursoTot.add( totalCorrenteRecurso1 );
		totalCorrenteRecursoTot = totalCorrenteRecursoTot.add(totalCorrenteRecurso2 );
		totalCorrenteRecursoTot = totalCorrenteRecursoTot.add(totalCorrenteRecurso3 );		
		acao.setTotalCorrenteRecursoTot( totalCorrenteRecursoTot );
		
		// coluna capital ano 1
		BigDecimal totalCapitalVlr1 = new BigDecimal(0);
		totalCapitalVlr1 = totalCapitalVlr1.add(acao.getValor1());
		totalCapitalVlr1 = totalCapitalVlr1.add(acao.getValor2());
		totalCapitalVlr1 = totalCapitalVlr1.add(acao.getValor3());
		acao.setTotalCapitalVlr1( totalCapitalVlr1 );
		
		// coluna capital ano 2
		BigDecimal totalCapitalVlr2 = new BigDecimal(0);
		totalCapitalVlr2 = totalCapitalVlr2.add(acao.getValor4());
		totalCapitalVlr2 = totalCapitalVlr2.add(acao.getValor5());
		totalCapitalVlr2 = totalCapitalVlr2.add(acao.getValor6());
		acao.setTotalCapitalVlr2( totalCapitalVlr2 );		
		
		// coluna capital ano 3
		BigDecimal totalCapitalVlr3 = new BigDecimal(0);
		totalCapitalVlr3 = totalCapitalVlr3.add(acao.getValor7());
		totalCapitalVlr3 = totalCapitalVlr3.add(acao.getValor8());
		totalCapitalVlr3 = totalCapitalVlr3.add(acao.getValor9());
		acao.setTotalCapitalVlr3( totalCapitalVlr3 );		

		// coluna capital ano 4
		BigDecimal totalCapitalVlr4 = new BigDecimal(0);
		totalCapitalVlr4 = totalCapitalVlr4.add(acao.getValor10());
		totalCapitalVlr4 = totalCapitalVlr4.add(acao.getValor11());
		totalCapitalVlr4 = totalCapitalVlr4.add(acao.getValor12());
		acao.setTotalCapitalVlr4( totalCapitalVlr4 );				
		
		// linha 1 total ( capital )
		BigDecimal totalCapitalRecurso1 = new BigDecimal(0);
		totalCapitalRecurso1 = totalCapitalRecurso1.add(acao.getValor3());
		totalCapitalRecurso1 = totalCapitalRecurso1.add(acao.getValor6());
		totalCapitalRecurso1 = totalCapitalRecurso1.add(acao.getValor9());
		totalCapitalRecurso1 = totalCapitalRecurso1.add(acao.getValor12());		
		acao.setTotalCapitalRecurso1( totalCapitalRecurso1 );	

		// linha 2 total ( capital )
		BigDecimal totalCapitalRecurso2 = new BigDecimal(0);
		totalCapitalRecurso2 = totalCapitalRecurso2.add(acao.getValor1());
		totalCapitalRecurso2 = totalCapitalRecurso2.add(acao.getValor4());
		totalCapitalRecurso2 = totalCapitalRecurso2.add(acao.getValor7());
		totalCapitalRecurso2 = totalCapitalRecurso2.add(acao.getValor10());		
		acao.setTotalCapitalRecurso2( totalCapitalRecurso2 );			
		
		// linha 3 total ( capital )
		BigDecimal totalCapitalRecurso3 = new BigDecimal(0);
		totalCapitalRecurso3 = totalCapitalRecurso3.add(acao.getValor2());
		totalCapitalRecurso3 = totalCapitalRecurso3.add(acao.getValor5());
		totalCapitalRecurso3 = totalCapitalRecurso3.add(acao.getValor8());
		totalCapitalRecurso3 = totalCapitalRecurso3.add(acao.getValor11());		
		acao.setTotalCapitalRecurso3( totalCapitalRecurso3 );			
		
		BigDecimal totalCapitalRecursoTot = new BigDecimal(0);
		totalCapitalRecursoTot = totalCapitalRecursoTot.add( totalCapitalRecurso1 );
		totalCapitalRecursoTot = totalCapitalRecursoTot.add( totalCapitalRecurso2 );
		totalCapitalRecursoTot = totalCapitalRecursoTot.add( totalCapitalRecurso3 );		
		acao.setTotalCapitalRecursoTot( totalCapitalRecursoTot );		
		
		
		// coluna total geral - linha 1
		BigDecimal totalGeralRecurso1 = new BigDecimal(0);
		totalGeralRecurso1 = totalGeralRecurso1.add( totalCorrenteRecurso1 );
		totalGeralRecurso1 = totalGeralRecurso1.add( totalCapitalRecurso1 );	
		acao.setTotalGeralRecurso1( totalGeralRecurso1 );
		
		// coluna total geral - linha 2
		BigDecimal totalGeralRecurso2 = new BigDecimal(0);
		totalGeralRecurso2 = totalGeralRecurso2.add( totalCorrenteRecurso2 );
		totalGeralRecurso2 = totalGeralRecurso2.add( totalCapitalRecurso2 );	
		acao.setTotalGeralRecurso2( totalGeralRecurso2 );

		// coluna total geral - linha 3
		BigDecimal totalGeralRecurso3 = new BigDecimal(0);
		totalGeralRecurso3 = totalGeralRecurso3.add( totalCorrenteRecurso3 );
		totalGeralRecurso3 = totalGeralRecurso3.add( totalCapitalRecurso3 );	
		acao.setTotalGeralRecurso3( totalGeralRecurso3 );
		
		BigDecimal totalGeralRecursoTot = new BigDecimal(0);
		totalGeralRecursoTot = totalGeralRecursoTot.add( totalGeralRecurso1 );
		totalGeralRecursoTot = totalGeralRecursoTot.add( totalGeralRecurso2 );
		totalGeralRecursoTot = totalGeralRecursoTot.add( totalGeralRecurso3 );		
		acao.setTotalGeralRecursoTot( totalGeralRecursoTot );	
		
		
	}		
	
	/**
	 * Calcula totais da tabela de resumo de itens de Programa para o Relatorio PPA por Programa
	 * @param programa
	 */
	public void visit(PPA_ProgramaBean programa){
		
		BigDecimal total = new BigDecimal(0);
		total = total.add(programa.getValor1());
		total = total.add(programa.getValor2());
		total = total.add(programa.getValor3());
		total = total.add(programa.getValor4());
		programa.setTotal( total );
		
	}
	
	/**
	 * Calcula totais da tabela de resumo de itens de Programa para o Relatorio PPA por Linha de acao
	 * @param item
	 */
	public void visit(PPA_LinhaAcaoBean item){
		
		BigDecimal total = new BigDecimal(0);
		total = total.add(item.getValor1());
		total = total.add(item.getValor2());
		total = total.add(item.getValor3());
		total = total.add(item.getValor4());
		item.setTotal( total );
		
	}
	
	/**
	 * Calcula totais da tabela de resumo de itens  para o Relatorio PPA por Orgao
	 * @param item
	 */
	public void visit(PPA_OrgaoBean item){
		
		BigDecimal total = new BigDecimal(0);
		total = total.add(item.getValor1());
		total = total.add(item.getValor2());
		total = total.add(item.getValor3());
		total = total.add(item.getValor4());
		item.setTotal( total );
		
	}	
	
	
}
