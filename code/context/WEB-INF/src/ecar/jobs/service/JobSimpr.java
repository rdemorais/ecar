package ecar.jobs.service;

import java.rmi.RemoteException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.PlanoAcaoServicePortProxy;
import br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsAcaoCadastro;
import br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsAtividadeCadastro;
import br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsUsuarioAutenticacao;
import ecar.dao.EstruturaDao;
import ecar.dao.ItemEstruturaDao;
import ecar.exception.ECARException;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ItemEstruturaIett;

public class JobSimpr implements Job{
	
	Logger logger = Logger.getLogger(this.getClass());
	
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
    	logger.debug("");
		Long init = System.currentTimeMillis();

        // Configure the SSLContext with a TrustManager
        SSLContext ctx;
		try {
			ctx = SSLContext.getInstance("TLS");
	        ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
	        SSLContext.setDefault(ctx);
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.getProperties().setProperty("http.proxySet", "true");
		System.getProperties().setProperty("http.proxyHost", "proxy");		
		
		PlanoAcaoServicePortProxy planoAcaoPorta = null;
		try{
			planoAcaoPorta= new PlanoAcaoServicePortProxy();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		WsUsuarioAutenticacao wsUsuarioAutenticacao = new WsUsuarioAutenticacao();

		wsUsuarioAutenticacao.setUsuario("75687909249");
		wsUsuarioAutenticacao.setSenha("BcADob2I");
		

		
		EstruturaEtt resul=null;
		EstruturaEtt prod=null;
		
		try {
			resul = (EstruturaEtt) new EstruturaDao(null).buscar(EstruturaEtt.class, 4L);
			prod = (EstruturaEtt) new EstruturaDao(null).buscar(EstruturaEtt.class, 5L);
		} catch (ECARException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
			
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cnpj", "00394544012787");
		params.put("nomeEmpresa", "Ministerio da Saude");
		//params.put("tpfa", (TipoFuncAcompTpfa) new TipoFuncAcompDao().buscar(TipoFuncAcompTpfa.class, 3L));
		
		ItemEstruturaDao dao = new ItemEstruturaDao(null);
		
		
		List<WsAcaoCadastro> listaAcao = dao.obtemListaAcao(params, resul, prod);
		
		StringBuffer csv = new StringBuffer();
		
		for(WsAcaoCadastro acao:listaAcao){
			
			csv.append(acao.toStringCSV()+"\n\r");
			
			try {

				br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsRetorno wsRetorno = planoAcaoPorta.cadastraAcao(wsUsuarioAutenticacao, acao);
				if(!wsRetorno.isSucesso()){
					
					System.out.println("Cadastro Acao:"+wsRetorno.getMensagensErro());
					
				}
				else System.out.println("Acao codigo: "+acao.getChvAcaoExterna()+" enviada com sucesso");
				
			} catch (RemoteException e) {
				System.out.println("Cadastro Acao:"+"Erro Conexao");
				e.printStackTrace();
			}
			
			ItemEstruturaIett iett = dao.getItemEtruturaById(Long.parseLong(String.valueOf(acao.getChvAcaoExterna())));
			
			boolean isIntermediario = false;
			
			isIntermediario = (iett.getEstruturaEtt().getCodEtt() == 5 ? false : true);
			
			//descricaoR5 est� sendo usada para exce��o do SOS Emergencias. Plano de Acao dos Hospitais
			if(iett.getDescricaoR5() == "EXC") {
				isIntermediario = false;
			}
			
			
			List<WsAtividadeCadastro> listaAtividade = dao.obtemListaAcaoTarefa(acao.getChvAcaoExterna(), true, isIntermediario);
//			if(listaAtividade.size()>0){
//				System.out.println(listaAtividade.size());
//			}


			for(WsAtividadeCadastro atividade:listaAtividade){
				csv.append(atividade.toStringCSV()+"\n\r");
				try {

					br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsRetorno wsRetorno = planoAcaoPorta.cadastraAtividade(wsUsuarioAutenticacao, atividade);
					if(!wsRetorno.isSucesso()){
						
						System.out.println("Cadastro Atividade:"+wsRetorno.getMensagensErro());
						
					}
					else System.out.println("Atividade codigo: "+atividade.getChvAtividadeExterna()+" enviada com sucesso");
					
				} catch (RemoteException e) {
					System.out.println("Cadastro Atividade:"+"Erro Conexao");
					e.printStackTrace();
				}
				
			}
			
			
		}



		/*
		//Arquivo excell
		File csvFile = new File("C://extratoSimPR.csv");
		
		try {
			csvFile.createNewFile();
			OutputStream os = new FileOutputStream(csvFile);
			os.write(csv.toString().getBytes());
			os.flush();
			os.close();				
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		

		//Fim Arquivo excell
		Long end = System.currentTimeMillis();
		Long diff = end - init;
		System.out.println("Demorou " + (diff / 1000) + " segundos");
		*/         
    }	
    
    private static class DefaultTrustManager implements X509TrustManager {
   	 
        @Override
        public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        }
 
        @Override
        public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        }
 
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
 
    }    

}
