package ecar.dao.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

import ecar.pojo.simpr.WsAcao;
import ecar.pojo.simpr.WsAcaoAtividade;
import ecar.pojo.simpr.WsAcaoCadastro;
import ecar.webservices.exc.EcarWSException;
import ecar.webservices.simpr.impl.EcarWS;

public class SimPRTest {
	
	//@Test
	public void testListaAcaoSimPR() {
		ecar.webservices.simpr.EcarWS ecarWs = new EcarWS();
		Integer[] ids;
		try {
			ids = ecarWs.listaAcao(0, 0, null);
			for (Integer i : ids) {
				System.out.print(i +",") ;
			}
			System.out.println("TAM: " + ids.length);
		} catch (EcarWSException e) {
		}
	}
	
	//@Test
	public void testBuscaAcaoCadastroSimPR() throws EcarWSException  {
		ecar.webservices.simpr.EcarWS ecarWs = new EcarWS();
		WsAcaoCadastro acaoCadastro = ecarWs.obtemAcaoCadastro(6246, null);
		System.out.println("########## obtemAcaoCadastro");
		System.out.println(acaoCadastro.toString());
		System.out.println("########## FIM obtemAcaoCadastro");
	}
	
	//@Test
	public void testObtemAcaoTarefa() throws EcarWSException {
		ecar.webservices.simpr.EcarWS ecarWs = new EcarWS();
		WsAcaoAtividade[] atividades = ecarWs.obtemAcaoAtividade(4720, null);
		System.out.println("########## obtemAcaoTarefa");
		for (WsAcaoAtividade wsAcaoAtividade : atividades) {
			System.out.println(wsAcaoAtividade.toString());
			System.out.println("-----------");
		}
		System.out.println("########## FIM obtemAcaoTarefa");
	}
	
	//@Test
	public void testObtemAcao() throws EcarWSException {
		ecar.webservices.simpr.EcarWS ecarWs = new EcarWS();
		WsAcao acao = ecarWs.obtemAcao(6334, null);
		System.out.println("########## obtemAcao");
		System.out.println(acao.toString());
		System.out.println("########## FIM obtemAcao");
	}
	
	//@Test	
	public void testCargarSimPR() throws EcarWSException {
		ecar.webservices.simpr.EcarWS ecarWs = new EcarWS();
		Integer[] ids;
		ids = ecarWs.listaAcao(0, 0, null);
		WsAcao acao;
		System.out.println("IDs carregados: " + ids.length);
		int cont = 0;
		for (int i = 0 ; i < ids.length ; i++) {
			acao = ecarWs.obtemAcao(ids[i], null);
			if(acao.getDadosCadastro() != null) {
				System.out.println("ACAO: " + ids[i] + "  " + (acao.getDadosCadastro() != null ? true : false) + " ATIV: " + (acao.getDadosAtividades().length > 0 ? true : false));
				cont++;
			}else {
				System.out.println("ACAO VAZIA: " + ids[i]);
			}
		}
		System.out.println("ACOES Cadastradas: " + cont);
	}
	
	//@Test
	public void testExtratoExcel()throws EcarWSException, IOException {
		ecar.webservices.simpr.EcarWS ecarWs = new EcarWS();
		Integer[] ids;
		ids = ecarWs.listaAcao(0, 0, null);
		WsAcao acao;
		System.out.println("IDs carregados: " + ids.length);
		for (Integer integer : ids) {
			System.out.println(integer + ", ");
		}
		StringBuffer csv = new StringBuffer();
		for (int i = 0 ; i < ids.length ; i++) {
			acao = ecarWs.obtemAcao(ids[i], null);
			if(acao.getDadosCadastro() != null) {
				csv.append("ACAO: " + acao.getDadosCadastro().toStringCSV() + "\n\r");	
			}else {
				csv.append("ACAO VAZIA: " + ids[i] + "\n\r");
			}
			WsAcaoAtividade[] atividades = acao.getDadosAtividades();
			for (int j = 0; j < atividades.length; j++) {
				if(atividades[j].getDadosCadastro() != null) {
					csv.append("ATIVIDADE: " + atividades[j].getDadosCadastro().toStringCSV() + "\n\r");	
				}else {
					csv.append("ATIVIDADE VAZIA \n");
				}
			}
		}
		
		File csvFile = new File("/home/rafael/extratoSimPR.csv");
		
		csvFile.createNewFile();
		
		OutputStream os = new FileOutputStream(csvFile);
		
		os.write(csv.toString().getBytes());
		
		os.flush();
		
		os.close();
	}
}