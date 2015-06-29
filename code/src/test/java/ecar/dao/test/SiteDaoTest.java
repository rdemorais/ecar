package ecar.dao.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import comum.util.EtiquetaUtils;

import ecar.dao.SiteDao;
import ecar.pojo.acompanhamentoEstrategico.ObjetivoEstrategico;
import ecar.pojo.acompanhamentoEstrategico.Resultado;
import ecar.pojo.acompanhamentoEstrategico.ResultadoStatusContar;

public class SiteDaoTest {

	private List<String> fonetizarEtiqueta(List<String> etiqueta) {
		List<String> etiquetaF = new ArrayList<String>();

		for (String et : etiqueta) {
			etiquetaF.add(EtiquetaUtils.fonetizar(et));
		}
		return etiquetaF;
	}

	@Test
	public void testaListaObjetivoEstrategicoFiltro() {

		SiteDao siteDao = new SiteDao();

		List<Integer> codigoObjetivoEstrategico = new ArrayList<Integer>();
		codigoObjetivoEstrategico.add(12);

		List<String> etiqueta = new ArrayList<String>();
		List<String> etiquetaPrioritaria = new ArrayList<String>();

		List<String> etiquetaF = fonetizarEtiqueta(etiqueta);

		List<Integer> resultadoStatus = new ArrayList<Integer>();
		
		resultadoStatus.add(1);
		resultadoStatus.add(0);

		List<Long> gruposUsuarios = new ArrayList<Long>();
		gruposUsuarios.add(134L);
		gruposUsuarios.add(19L);

		Long codUsuario = 1L;

		Long exercicio = 12L;

		int painelIndicador = 0;

		Long acompReferencia = 0L;

		boolean prioritario = false;

		List<Long> secretarias = new ArrayList<Long>();
		
		List<ObjetivoEstrategico> objetivosEstrategicos = new ArrayList<ObjetivoEstrategico>();

		// secretarias.add(new Long(14));
//
//	listarObjetivoEstrategicoFiltroComIndicadorMaisRapidoEuEspero(List<Integer> codigoObjetivoEstrategico, List<String> etiqueta, List<String> etiquetaPrioritaria,
//		List<Integer> resultadoStatus, Long codigoUsuario, List<Long> gruposUsuarios, boolean isIndicador, boolean apenasParecer, boolean prioritario, List<Long> secretarias,
//		boolean relEncaminhamento, Date dataInicial, Date dataFinal, Long exercicio, boolean somenteREM, Long acompReferencia, boolean isDetalhamento

		//List<Integer> codigoObjetivoEstrategico, List<String> etiqueta, List<String> etiquetaPrioritaria, List<Integer> resultadoStatus, Long codigoUsuario, List<Long> gruposUsuarios, boolean isIndicador, boolean apenasParecer, boolean prioritario, List<Long> secretarias, boolean relEncaminhamento, Date dataInicial, Date dataFinal, Long exercicio, boolean somenteREM, Long acompReferencia, boolean isDetalhamento		
		
		Resultado resultado = null;

		try {
		/*
		
			objetivosEstrategicos = siteDao.listarObjetivoEstrategicoFiltroComIndicadorMaisRapidoEuEspero(
					codigoObjetivoEstrategico, 
					etiqueta, 
					etiquetaPrioritaria, 
					resultadoStatus, 
					codUsuario, 
					gruposUsuarios, 
					false, 
					false, 
					false, 
					secretarias, 
					false, 
					null, 
					null, 
					exercicio, 
					false, 
					acompReferencia, 
					true, 
					0);
			*/
		
			resultado = siteDao.listarResultado(10140, codUsuario, gruposUsuarios, "b8e04cfc-c238-48de-84e6-e3763ea0096e%3E1%3E134,19", 12L, null);
			System.out.println(resultado.getParecer());
//			Produto produto = siteDao.listarProduto(6262, codUsuario, gruposUsuarios, "b8e04cfc-c238-48de-84e6-e3763ea0096e%3E1%3E134,19", exercicio, acompReferencia);
			
//			siteDao.getIndicadoresSite(6261L);
			
//			System.out.println(resultado);
//			for(Produto produto : resultado.getProdutos()){
//				System.out.println("produto:   " + produto);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}		

//		System.out.println("Resultado: " + resultado.toString());
////		for (ObjetivoEstrategico oe : objetivosEstrategicos) {
////			System.out.println("codigo : " + oe.getCodigo() + ", nome: " + oe.getNome());
////		}

	}
	
	//@Test
	public void testResultadoStatus() {
		SiteDao dao = new SiteDao();
		List<ResultadoStatusContar> status = dao.listarResultadoStatus(null, 11L);
		
	}

}