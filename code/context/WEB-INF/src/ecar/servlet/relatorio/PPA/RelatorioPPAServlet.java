package ecar.servlet.relatorio.PPA;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;

import comum.util.Pagina;

import ecar.servlet.relatorio.PPA.bean.RelatorioPPABean;
import ecar.util.jasper.JasperService;
import ecar.util.jasper.servlet.RelatorioServlet;

/**
 * Servlet responsavel por interagir com os eventos de geracao de relatorio PPA
 * @author gabrielsolana
 *
 */
public class RelatorioPPAServlet extends RelatorioServlet {
	
	private static final long serialVersionUID = -1766348930967499449L;
	private Logger logger = Logger.getLogger(this.getClass());


        /**
         *
         */
        public RelatorioPPAServlet(){
		super();
	}
	
        /**
         *
         * @param request
         * @param response
         */
        public void doRelatorioGet(HttpServletRequest request, HttpServletResponse response) {
		doRelatorioPost(request,response);
	}

        /**
         *
         * @param request
         * @param response
         */
        public void doRelatorioPost(HttpServletRequest request, HttpServletResponse response) {

		logger.info("Generating relatorioPPA...");

		final ServletContext context = getServletContext();
		
		String tipoParametro = Pagina.getParamStr(request, "relatorio");
		logger.info( "Parametro escolhido:::: " +  tipoParametro );
		
		String[] programas = request.getParameterValues("programa");
		String orgao = Pagina.getParamStr(request, "listOrgao");
		final String criteriosIncluidosCom = Pagina.getParamStr(request, "criteriosIncluidosCom");
		final String criteriosIncluidosSem = Pagina.getParamStr(request, "criteriosIncluidosSem");
		String data = Pagina.getParamStr(request, "data");
		String pagina = Pagina.getParamStr(request, "pagina");
		String titulo = Pagina.getParamStr(request, "titulo");		
		
		Integer paginaInt = null;
		
		try {
			if( pagina ==null || pagina.equals("") ){
				paginaInt = Integer.valueOf(1);
			}else{
				paginaInt = Integer.valueOf( pagina );
			}
		} catch (Exception e) {
			paginaInt = Integer.valueOf( 1 );
		}
		
		try {
			if( data ==null || data.equals("") ){
				data = "";
			}
		} catch (Exception e) {
			data = "";
		}			
		
		try {
			if( titulo ==null || "".equals( titulo ) ){
				titulo = "ESTADO DO PARANÁ - PLANO PLURIANUAL 2008-2011";	
			}
		} catch (Exception e) {
			titulo = "ESTADO DO PARANÁ - PLANO PLURIANUAL 2008-2011";
		}			
		
		try {
			if( tipoParametro ==null || "".equals( tipoParametro ) ){
				tipoParametro = "completo";	
			}
		} catch (Exception e) {
			tipoParametro = "completo";
		}	

		try {
			if( orgao ==null || "".equals( orgao ) ){
				orgao = "0";	
			}
		} catch (Exception e) {
			orgao = "0";
		}	
		
		 programas= programas==null?new String[1]:programas;
		
		
		Map parametros = new HashMap();
		try {
		
	    	String[] criteriosParaFiltrarCom = (!"".equals(criteriosIncluidosCom.trim())) ? criteriosIncluidosCom.split("\\|") : new String[] {};
	    	String[] criteriosParaFiltrarSem = (!"".equals(criteriosIncluidosSem.trim())) ? criteriosIncluidosSem.split("\\|") : new String[] {};

	    	ArrayList<String> listaCriteriosCom = new ArrayList<String>();					
	    	ArrayList<String> listaCriteriosSem = new ArrayList<String>();					

	    	for(int i = 0; i < criteriosParaFiltrarCom.length; i++){
	    		listaCriteriosCom.add(criteriosParaFiltrarCom[i]);
	    	}        

	    	for(int i = 0; i < criteriosParaFiltrarSem.length; i++){
	    		listaCriteriosSem.add(criteriosParaFiltrarSem[i]);
	    	}   			
		
	    	ArrayList<String> listaProgramas = new ArrayList<String>();
	    	for(int i = 0; i < programas.length; i++){
	    		listaProgramas.add(programas[i]);
	    	}
	    	
			final RelatorioPPAService service = RelatorioPPAService.getInstance(request);
						
			// path absoluto do relatorio PPA
			final String pathRelPPA = context.getRealPath( JasperService.PATH_RELATORIO + "/relatorioPPA_projetoLei.jasper" );
			final String pathRelProgramaPPA = context.getRealPath( JasperService.PATH_RELATORIO + "/relatorioPPA_projetoLei_programa.jasper" );
			final String pathRelIndicadorPPA = context.getRealPath( JasperService.PATH_RELATORIO + "/relatorioPPA_projetoLei_tabela_indic.jasper" );
			final String pathRelAcaoPPA = context.getRealPath( JasperService.PATH_RELATORIO + "/relatorioPPA_projetoLei_acao.jasper" );
			final String pathRelProdutoPPA = context.getRealPath( JasperService.PATH_RELATORIO + "/relatorioPPA_projetoLei_tabela_produto.jasper" );
			final String pathRelPPACapa = context.getRealPath( JasperService.PATH_RELATORIO + "/relatorioPPA_projetoLei_capa.jasper" );
			
			JasperReport relatorioProjetoLei = JasperService.loadReport( pathRelPPA );
			JasperReport relatorioCapa = JasperService.loadReport( pathRelPPACapa );
			
			//printParam( tipoParametro, listaProgramas, orgao, listaCriteriosCom, listaCriteriosSem, data,pagina,titulo);
			
			ArrayList<RelatorioPPABean> relatorioDS = service.generatePPA( tipoParametro, listaProgramas, orgao, listaCriteriosCom, listaCriteriosSem);
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(relatorioDS);
			
			// Parametros
			parametros.put("pagina", paginaInt );
			parametros.put( "data" , data );
			parametros.put( "titulo" , titulo );
			parametros.put( "relatorioPPA_projetoLei_programas_PATH" , pathRelProgramaPPA );
			parametros.put( "relatorioPPA_projetoLei_indicadores_PATH" , pathRelIndicadorPPA );
			parametros.put( "relatorioPPA_projetoLei_acoes_PATH" , pathRelAcaoPPA );
			parametros.put( "relatorioPPA_projetoLei_produtos_PATH" , pathRelProdutoPPA );				
			
			JasperPrint capa = JasperService.fillReport(relatorioCapa,parametros );
			JasperPrint relProjetoLei = JasperService.fillReport(relatorioProjetoLei,parametros, ds);
			logger.info( "Numero paginas projetoLei sem Capa:" +  JasperService.getNumberOfPages(relProjetoLei) );
			
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			JasperService.addRelatorio( capa, relProjetoLei, out );
			
			logger.info("Relatorio gerado com sucesso!");
			this.responseToPDF(response, out.toByteArray() );
			
		} catch (Exception e) {
			logger.error( "Nao foi possivel gerar relatorioPPA",e);
		}
		
	}
	

	private void printParam( String tipoParametro, ArrayList listaProgramas, String orgao, ArrayList listaCriteriosCom, ArrayList listaCriteriosSem, String data, String pagina, String titulo ){
		logger.info( "tipoParametro: " + tipoParametro );
		logger.info( "orgao: " + orgao );
		logger.info( "titulo: " + titulo );
		logger.info( "pagina: " + pagina );
		logger.info( "data: " + data );
		
		logger.info( "listaCriteriosSem:::::::::::" );
		for (Iterator iter = listaCriteriosSem.iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			logger.info( element );
		}

		logger.info( "listaCriteriosCom:::::::::::" );
		for (Iterator iter = listaCriteriosCom.iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			logger.info( element );
		}
		
		logger.info( "listaProgramas:::::::::::" );		
		for (Iterator iter = listaProgramas.iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			logger.info( element );
		}
		
	}
	

	
}
