package ecar.dao.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ecar.pojo.acompanhamentoEstrategico.Comentario;
import ecar.pojo.acompanhamentoEstrategico.Estrategia;
import ecar.pojo.acompanhamentoEstrategico.ObjetivoEstrategico;
import ecar.pojo.acompanhamentoEstrategico.Produto;
import ecar.pojo.acompanhamentoEstrategico.Resultado;
import ecar.pojo.acompanhamentoEstrategico.ResultadoStatusContar;
import ecar.webservices.pems.EcarWSPE;

/**
 * 
 * @author Rafael de Morais
 *
 */
public class BijariWSTest {	
	
	//@Test
	public void testBuscaResultado() {
		System.out.println("############# testBuscaResultado");
		EcarWSPE ecarWSPE = new ecar.webservices.pems.impl.EcarWSPE();
		Resultado res = ecarWSPE.buscaResultado(null, 1888, null, null, null);
		System.out.println(res);
	}

//	@Test
	public void testGravarComentario() {
		System.out.println("############# testGravarComentario");
		EcarWSPE ecarWSPE = new ecar.webservices.pems.impl.EcarWSPE();
		ecarWSPE.gravarComentario(null, 1826, "Teste Gravação com prazo e responsável 3",false,"08/10/2012",2/*"teste%20de%20acentua%E7%E3o-%20caf%E9%20-%20%E1%20-%20%3E%20%3C%20@%23"*/);
//		ecarWSPE.gravarComentario(null, 1826, "Teste Gravação com valores Null",null,null,null/*"teste%20de%20acentua%E7%E3o-%20caf%E9%20-%20%E1%20-%20%3E%20%3C%20@%23"*/);
		System.out.println("############# FIM testGravarComentario");
	}
	
	//@Test
	public void testExcluirComentario() {
		System.out.println("############# testExcluirComentario");
		EcarWSPE ecarWSPE = new ecar.webservices.pems.impl.EcarWSPE();
		ecarWSPE.excluirComentario(null, 439);
		System.out.println("############# FIM testExcluirComentario");
	}
	
	//@Test
	public void testBuscaComentario() {
		System.out.println("############# testBuscaComentario");
		EcarWSPE ecarWSPE = new ecar.webservices.pems.impl.EcarWSPE();
		List<Comentario> comentarios = ecarWSPE.buscaComentario(null, 1826/*2385*/);
		for (Comentario comentario : comentarios) {
			System.out.println(comentario.toString());
		}
		System.out.println("############# FIM testBuscaComentario");
	}
	
//	@Test
	public void testMarcarDesmarcarAtencaoMinistro() {
		System.out.println("############# testAtencaoMinistro");
		EcarWSPE ecarWSPE = new ecar.webservices.pems.impl.EcarWSPE();
		ecarWSPE.marcarAtencaoMinistro(null, 1826);
		System.out.println("############# FIM testAtencaoMinistro");
	}
		
	//@Test
	public void testListarObjetivoEstrategicoFiltro() {
		System.out.println("############# testListarObjetivoEstrategicoFiltro");
		EcarWSPE ecarWSPE = new ecar.webservices.pems.impl.EcarWSPE();
		
		List<Integer> codigoObjetivoEstrategico = new ArrayList<Integer>();
//		codigoObjetivoEstrategico.add(7);
		
		List<String> etiqueta = new ArrayList<String>();
		
		List<String> etiquetaP = new ArrayList<String>();
//		etiquetaP.add("PRIORITARIO");
//		etiquetaP.add("SAS");
		
		List<Integer> resultadoStatus = new ArrayList<Integer>();
		
		String admin = "55e076da-e389-4467-8acf-93038631c22f>1>134,19";
		String dandara = "b14a01f1-365b-4ffd-8470-d330fed5be4e>64>125";
		int qtO = 0;
		int qtE = 0;
		int qtR = 0;
		int qtP = 0;		
		
		List<ObjetivoEstrategico> lista = 
			ecarWSPE.buscaObjetivoEstrategicoFiltro(admin, codigoObjetivoEstrategico, etiqueta, etiquetaP, resultadoStatus, null,0);
		
		for (ObjetivoEstrategico objetivoEstrategico : lista) {
			qtO = lista.size();
			System.out.println("OE -> " + 
					objetivoEstrategico.getSiglaIett() + " - " + objetivoEstrategico.getNome());
			qtE += objetivoEstrategico.getEstrategias().size();
			
			for (Estrategia estrategia : objetivoEstrategico.getEstrategias()) {				
				System.out.println("\t E ->"+ estrategia.getSiglaEstrategia() + "-"+ estrategia.getNome());
				qtR += estrategia.getResultados().size();
				
				for (Resultado resultado : estrategia.getResultados()) {
					System.out.println("\t \t R -> " + resultado.getSiglaResultado()+"-"+ resultado.getNome() + " P: " + resultado.getPrioritario() + " A: " + resultado.isAtencao());
					
					if(resultado.periodoAcompanhamento != null){
						System.out.println("COR: " + resultado.periodoAcompanhamento.get(0).getNomeCor());
						System.out.println("Mes: " + resultado.periodoAcompanhamento.get(0).getMes());
						System.out.println("ANO: " + resultado.periodoAcompanhamento.get(0).getAno());
					}else {
					System.out.println("COR: NÃO MONITORADO ");	
					}	
//					
//					String nome = "";
//					String co = "";
//					if(resultado.responsavel!=null) {
//						nome = resultado.responsavel.getNome();						
//					}
//					for (Responsavel responsavel : resultado.getCoResponsavel()) {
//						co = responsavel.getNome() + ", ";
//					}
//					System.out.println("\t \t -> Resp: - " + nome);
//					System.out.println("\t \t -> CO Resp: - " + co);
					qtP += resultado.getProdutos().size();
					
					for (Produto produto : resultado.getProdutos()) {
						System.out.println("\t \t \t P-> " + produto.getSiglaProduto()+"-"+ produto.getNome() );						
						System.out.println("Mes: " + produto.periodoAcompanhamento.get(0).getMes());
						System.out.println("ANO: " + produto.periodoAcompanhamento.get(0).getAno());
//						if(produto.periodoAcompanhamento != null){
//							System.out.println("COR: " + produto.periodoAcompanhamento.get(0).getNomeCor());	
//						}else {
//						System.out.println("COR: NÃO MONITORADO ");	
//						}						
//						
//						
//						if(produto.responsavel!=null) {
//							nome = produto.responsavel.getNome();						
//						}
//						for (Responsavel responsavel : produto.getCoResponsavel()) {
//							co = responsavel.getNome() + ", ";
//						}
//						System.out.println("\t \t -> Resp: - " + nome);
//						System.out.println("\t \t -> CO Resp: - " + co);
					}
				}
			}
		}
		System.out.println("OE - >" + qtO);
		System.out.println("E - >" + qtE);
		System.out.println("R - >" + qtR);
		System.out.println("P - >" + qtP);		
		System.out.println("############# FIM testListarObjetivoEstrategicoFiltro");
	}
	
//	@Test
	public void testLoginWS() {
		System.out.println("############# testLoginWS");
		EcarWSPE ecarWSPE = new ecar.webservices.pems.impl.EcarWSPE();
		ecarWSPE.loginWS("admin", "432062e8063483ce673c781c2b103c95"/*"d76e7e08f375d008208c77cd0648be79"*/);
		System.out.println("############# FIM testLoginWS");		
	}
	
//	@Test
	public void testBuscaEtiqueta() {
		System.out.println("############# testBuscaEtiqueta");
		EcarWSPE ecarWSPE = new ecar.webservices.pems.impl.EcarWSPE();
		ecarWSPE.buscaEtiqueta("f02e85b9-6670-4565-9601-f61141b25021;1;134,19", "");
		System.out.println("############# FIM testBuscaEtiqueta");		
	}
	
//	@Test
	public void testLogOffWS() {
		System.out.println("############# logOffWS");
		EcarWSPE ecarWSPE = new ecar.webservices.pems.impl.EcarWSPE();
		ecarWSPE.logOffWS("e954c205-882a-4ead-b911-097bbeea8d40");
		System.out.println("############# FIM logOffWS");		
	}
	
//	@Test
	public void testBuscaObjetivoEstrategicoLista() {
		System.out.println("############# BuscaObjetivoEstrategicoLista");
		EcarWSPE ecarWSPE = new ecar.webservices.pems.impl.EcarWSPE();
		List<ObjetivoEstrategico> oes= ecarWSPE.buscaObjetivoEstrategicoLista("55e076da-e389-4467-8acf-93038631c22f>1>134,19");
		System.out.println("TAM: "+oes.size());
		for (ObjetivoEstrategico objetivoEstrategico : oes) {
			System.out.println(objetivoEstrategico.getNome());
		}
		System.out.println("############# FIM BuscaObjetivoEstrategicoLista");		
	}
	
	//@Test
	public void testBuscaResultadoStatus() {
		System.out.println("############# testBuscaResultadoStatus");
		EcarWSPE ecarWSPE = new ecar.webservices.pems.impl.EcarWSPE();
		
		List<ResultadoStatusContar> res = ecarWSPE.buscaResultadoStatus("55e076da-e389-4467-8acf-93038631c22f>1>134,19", null);
		for (ResultadoStatusContar resultadoStatusContar : res) {
			System.out.println(resultadoStatusContar.getNome() + " " + resultadoStatusContar.getContar());
		}
		
		System.out.println("############# FIM testBuscaResultadoStatus");
	}
}