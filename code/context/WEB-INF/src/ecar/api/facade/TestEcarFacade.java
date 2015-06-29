package ecar.api.facade;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import comum.database.HibernateUtil;

import ecar.dao.AcompRealFisicoDao;
import ecar.dao.EstruturaDao;
import ecar.dao.FuncaoDao;
import ecar.dao.TipoFuncAcompDao;
import ecar.exception.ECARException;
import ecar.pojo.EstruturaAtributoEttat;
import ecar.pojo.FuncaoFun;
import ecar.pojo.ItemEstruturaIett;
import ecar.util.Dominios;


/**
 * Classe de teste para testar as classes wrapper. Essa classe é
 * dependente do banco de dados, infelizmente não consegui utilizar dbunit
 * para testes mais sofisticados. Se vc tentar usar vai falhar.
 * 
 * @author 82035644020
 *
 */
public class TestEcarFacade {
	
	
	public static void testEstrutura() throws ECARException{
		Estrutura est = new Estrutura("Fundos de Investimentos");
		
		List<ItemEstruturaIett> l = est.getItensDaEstrutura();
		
		for(ItemEstruturaIett item: l){
			System.out.println(est.getNome() + " -> " + item.getNomeIett());
		}
	}
	
	
	
	public static void testeGetLocais() throws ECARException{
		/**
		 * Pega um indicador e ver os seus valores previstos por local
		 * 
		 */
		
		IndicadorResultado indicador = new IndicadorResultado(1);
		//pega os meses do intem indicador
		List<EcarData> meses = indicador.getItemEstrutura().getMeses(2010);
		//pega os locais do indicador
		List<Local> locais = indicador.getItemEstrutura().getLocais();

		System.out.println(indicador.getNome());
		
		
		for(Local local: locais){
			local.getFilhos().get(0).getAbrangencia();
			for(EcarData data: meses){
				System.out.println(local.getNome() + " Mes: " + data.getDataFormatada() + " Valor: " + indicador.getPrevistoPorLocal(data, local).getValorPrevisto());
			}
			System.out.println("");
		}
		
	}
	
	
	public static void testGetRealizadosIndicador() throws ECARException {
		IndicadorResultado indicador = new IndicadorResultado(2);
		List<Realizado> realizados = indicador.getAcompanhamentosRealizado();
		
		System.out.println("Realizados pelo indicador");
		System.out.println(indicador.getRealObject().getIndRealPorLocal());
		
		for(Realizado realizado: realizados){
			List<RealizadoLocal> l = indicador.getAcompanhamentoFisicoLocal(realizado);
			for(RealizadoLocal o: l){
				System.out.println(o.getLocal().getNome() + "\t" + o.getLocal().getSigla() + "\t" + o.getLocal().getCodigoIBGE() + "\t" + o.getRealizado());
			}
		}
		
		//testando por exercicios
		//System.out.println("Previsto e realizado por exercicio");
		Exercicio exercicio = new Exercicio(8);
		//System.out.println(indicador.getPrevistoNoExercicio(exercicio));
		System.out.println("-> " + indicador.getRealizadoMensal(exercicio));
		System.out.println("Meses do item");
		System.out.println(indicador.getItemEstrutura().getMeses());

		System.out.println(indicador.getDescricaoTipoIndicador());
		
	}
	
	
	public static void testeHierarquia() throws ECARException{

		int BRASIL = 32;
		Local local = new Local(BRASIL);
			
		System.out.println(local.getFilhos(Local.PAIS));
		System.out.println(local.getFilhos(Local.REGIAO));
		System.out.println(local.getFilhos(Local.ESTADO));
		System.out.println(local.getFilhos(Local.MUNICIPIO));
		
	}
	
	
	public static void testeItemEstruturaHierarquiaLocais() throws ECARException{
		ItemEstrutura item = new ItemEstrutura(2);
		System.out.println(item.getNome());
		System.out.println("Agrangência: " + item.getAbrangenciaAsString());
		System.out.println(item.getLocais());
		//pega os municípios dos estados cadastrados de todas as regiões
		//de todos os países se existirem
		System.out.println(item.getLocais(Local.MUNICIPIO));
	}
	
	
	public static void testeGetPrevistoRealizadoMensal() throws ECARException {
		
    	AcompanhamentoItemEstrutura acompanhamento =  new AcompanhamentoItemEstrutura(87);
    	IndicadorResultado indicador = new IndicadorResultado(1);
    	Exercicio exercicio = new Exercicio(8);
    
    	acompanhamento.getPeriodoAcompanhamento().getExercicio();
    	List<EcarData> meses = null;
    	
    	if(exercicio.equals(acompanhamento.getPeriodoAcompanhamento().getExercicio())){
    		String mesRef = acompanhamento.getPeriodoAcompanhamento().getMesReferencia();
    		String anoRef = acompanhamento.getPeriodoAcompanhamento().getAnoReferencia();
    		meses = exercicio.getMeses();
    	}

    	AcompRealFisicoDao dao = new AcompRealFisicoDao(null);
    	
    	for(EcarData data: meses){
    		System.out.println(data.getDataFormatadaComBarra() + ": " + indicador.getRealizadoMensal(data.getMes(), data.getAno()));
    		System.out.println(dao.getQtdRealizadaMesAno(indicador.getRealObject(), (long)data.getMes(), (long)data.getAno()));
    	}
    	
    	Map<EcarData, Double> l = indicador.getRealizadoMensal(exercicio);
    	
    	for(EcarData data: l.keySet()){
    		System.out.println(data.getDataFormatada() + " " + l.get(data));
    	}
    	
    	
		for(Previsto prev: indicador.getValoresPrevistosPorMes()){
			System.out.println(prev);
		}

	}
	

	public static void testeGetPrevistoPorLocal() throws ECARException  {
    	
		System.out.println("Previsto por local");
		IndicadorResultado indicador = new IndicadorResultado(1);
    	System.out.println(indicador.isIndicadorPorLocal());
    	System.out.println(indicador.getPrevistosPorLocal());
    	
    	
	}
	
	
	
	public static void testeGetAcompanhamentosIndicador() throws ECARException{
		Estrutura estrutura = new Estrutura("Fundos de Investimentos");
		ItemEstrutura myItem = new ItemEstrutura(estrutura.getItemDaEstruturaPorNome("XP Investor FIA"));
		List<IndicadorResultado> indicadores = myItem.getIndicadoresResultado();
		
		System.out.println("\n\n---------------Indicadores ------------------------");
		for(IndicadorResultado ind : indicadores){
			System.out.println("id: " + ind.getId() + "\tNome: " + ind.getNome() + "\t Acumulavel: " + ind.isAcumulavel() + "\t Ativo:" + ind.isAtivo());
		}
		
		
		System.out.println("\n\n-------------Acompanhamento--------------------");
		//pega os acompanamentos 
		for(AcompanhamentoItemEstrutura acomp: myItem.getListaDeAcompanhamentos()){
			System.out.println("Acomp. id: " + acomp.getId() +  "Data Inicial: "  + acomp.getDataInicial() + "Data Limite: " + acomp.getDataLimite()  );
			PeriodoAcompanhamento periodo = acomp.getPeriodoAcompanhamento();
			System.out.println("Período:");
			System.out.print(" Mes Referencia: " + periodo.getMesReferencia());
			System.out.println(" Ano Referencia: " + periodo.getAnoReferencia());
		}

		
		
		
	}
	

	
	public static void testAcompanhamentoReferencia() throws ECARException{
		AcompanhamentoItemEstrutura acompanhamento = new AcompanhamentoItemEstrutura(92);
		System.out.println(acompanhamento.getMesReferencia() + " " + acompanhamento.getAnoReferencia());
		System.out.println("Data Inicio: " + acompanhamento.getItemEstrutura().getDataInicial());
		System.out.println("Data Fim: " + acompanhamento.getItemEstrutura().getDataFinal());
		List<EcarData> meses = acompanhamento.getItemEstrutura().getMeses(Integer.valueOf(acompanhamento.getAnoReferencia()));
		System.out.println(meses);
	}
	
	
	
	public static void testeAcompanhamentoItemEstrutura()throws Exception{
		ItemEstrutura item = new ItemEstrutura(9);

		for(AcompanhamentoItemEstrutura acomp: item.getListaDeAcompanhamentos()){
			System.out.println(acomp.getMesReferencia() + " " + acomp.getAnoReferencia());
			for(Realizado real :item.getRealizados(acomp, Dominios.TODOS)){
				if(real != null){
				  System.out.println(real.getMes() + " " + real.getAno() + " " + real.getRealizado());
				}
			}
		}
	}

	
	public static void testMudancaDeDatasItemEstrutura() throws ECARException{
		ItemEstrutura item = new ItemEstrutura(8);
		System.out.println(item.getEstrutura().getId());
		
		System.out.println(item.getNome());
		System.out.println("Data Inicial: " + item.getDataInicial());
		System.out.println("Data Final: " + item.getDataFinal());
  
		
		System.out.println("Listando Indicadores com previstos e realizados");
		
		for(IndicadorResultado indicador: item.getIndicadoresResultado()){
			System.out.println("--------------------------------------");
			System.out.println(indicador.getNome());
			System.out.println("Acompanhamentos");
			System.out.println(indicador.getAcompanhamentosRealizado());
			System.out.println("Previstos por mes");
			System.out.println(indicador.getValoresPrevistosPorMes());
			System.out.println("Previstos por local");
			System.out.println(indicador.getPrevistosPorLocal());
			System.out.println("--------------------------------------");
		}
		
		
		String start = "01/12/2009";
		String end = "30/09/2010";
		
		System.out.println("Mudando data para " + start + " e " + end);
		
		if(item.podeAlterarData(start, end) == false){
			System.out.println(item.getErrorKey());
		}

		String keyAndProperties = item.getErrorKey();
		int length = keyAndProperties.split("#").length;
		String key = keyAndProperties.split("#")[0];
		String parameters[] = new String[length-1];
		
		for(int i=1; i < length; i++){
			parameters[i-1] = keyAndProperties.split("#")[i]; 	
		}
		
		
	}
	
	
	public static void testGetFuncaoEstrutura() throws ECARException{
		 TipoFuncAcompDao dao  = new TipoFuncAcompDao(null);
		 Estrutura est = new Estrutura(13);
		 
		 List lista1 = dao.getFuncaoAcompOrderByEstruturasAtivasInativas();
		 
	}
	
	
	public static void testGetAtributosEstruturaByFuncao() throws ECARException {
		
		Estrutura est = new Estrutura(5);
		FuncaoDao dao = new FuncaoDao(null);
		FuncaoFun dadosGerais = dao.getFuncao((long)23);
		
		EstruturaDao estDao = new EstruturaDao(null);
		
		EcarTimer time1 = new EcarTimer();
		
		time1.start();
		List atributos = estDao.getAtributosEstrutura(est.getRealObject(), dadosGerais);
		time1.stop();
		
		System.out.println(time1.getEllapsedTimeInMillis() + " ms");
		
		for(Object att: atributos){
			System.out.println(((EstruturaAtributoEttat)att).getLabelEstruturaEttat());
		}
				
		
	}
	
	
	public static void getAnexos() throws ECARException{
		ItemEstrutura item = new ItemEstrutura(7);
		for(EcarAnexo anexo: item.getAnexos()){
			System.out.println(anexo);
		}
	}
	

	public static void testeConclusaoDeIndicador() throws Exception {
//		IndicadorResultado indicador = new IndicadorResultado(27);
//		System.out.println("Concluído? " + indicador.isConcluido());
//		System.out.println("Data da Conclusão " + indicador.getDataConclusao().getDataFormatadaMesExtenso());
//		System.out.println("Valor da Conclusão " +  indicador.getValorRealizadoConclusao().getRealizadoFormatado());
//		
		ItemEstrutura item = new ItemEstrutura(13);
		for(IndicadorResultado i: item.getIndicadoresConcluidos()){
			System.out.println(i.getNome() + " concluido em: " + i.getDataConclusao());
		}

		System.out.println("\nLista de Indicadores Concluídos:\n");
		
		
		//pega os indicadores concluídos antes de março
		//colocando true, obtem-se os indicadores concluídos inclusive em março
		for(IndicadorResultado i: item.getIndicadoresConcluidos(new EcarData(3,2011), true)){
			System.out.println(i.getNome() + " concluido em: " + i.getDataConclusao());
		}

		System.out.println("\nLista de indicadores não concluídos:\n");
		
		for(IndicadorResultado i: item.getIndicadoresNaoConcluidos()){
			System.out.println(i.getNome() + " concluido em: " + i.getDataConclusao());
		}

		System.out.println("\nLista de indicadores não concluídos até MAR/2011 :\n");
		for(IndicadorResultado i: item.getIndicadoresNaoConcluidos(new EcarData(3,2011))){
			System.out.println(i.getNome() + " concluido em: " + i.getDataConclusao());
		}


		
		
	}
	
	
	public static void testGetIndicadoresConcluidosOptimizedQuery() throws ECARException{
	
		ItemEstrutura item = new ItemEstrutura(13);
		List<IndicadorResultado> list_2 = item.getIndicadoresConcluidos();

		if(list_2 != null){ System.out.println(list_2);} 
		
		
	}
	
	
	
	
	
	public static void main(String[] args){
		try {
			Session sessao = HibernateUtil.currentSession();
			
			//testEstrutura();
			//testeGetLocais();
			//testeHierarquia();
			//testGetRealizadosIndicador();
			//testeGetPrevistoRealizadoMensal();
			//testeItemEstruturaHierarquiaLocais();
			//testeGetAcompanhamentosIndicador();
			//testeGetPrevistoPorLocal();
			//testAcompanhamentoReferencia();
			//testeAcompanhamentoItemEstrutura();
			//testMudancaDeDatasItemEstrutura();
			//testGetFuncaoEstrutura();
			//testGetAtributosEstruturaByFuncao();
			//getAnexos();
			//testeConclusaoDeIndicador();	
			testGetIndicadoresConcluidosOptimizedQuery();
			
			
			
//			ItemEstrutura item = new ItemEstrutura(5);
//			item.getListaDeAcompanhamentos();
//			
//			System.out.println(item.getEstrutura().getLabelAtributo("dataTerminoIett"));
//			
//			
//			Exercicio exe = new Exercicio(1);
//			boolean r = exe.podeAlterarDatasLimites("01/01/2004", "30/01/2004");
//			if(!r){
//				System.out.println(exe.getErrorKey());	
//			}else{
//				System.out.println("pode alterar as datas");
//			}
			

			
			
			
		} catch (Exception e) {
			System.out.println("-------------------");
			e.printStackTrace();
		}
	}

	
}
