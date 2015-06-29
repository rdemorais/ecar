package ecar.servlet.relatorio.PPA_LinhaAcao;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;

import ecar.util.jasper.JasperService;
import ecar.util.jasper.servlet.RelatorioServlet;

/**
 * Servlet responsavel por interagir com os eventos de geracao de relatorio PPA
 * @author gabrielsolana
 *
 */
public class RelatorioPPALinhaAcaoServlet extends RelatorioServlet {
	
	private static final long serialVersionUID = 3213904736460171482L;
	private Logger logger = Logger.getLogger(this.getClass());

        /**
         *
         */
        public RelatorioPPALinhaAcaoServlet(){
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

		logger.info("Generating relatorio PPA Por Linha de A��o...");
		
		final ServletContext context = getServletContext();
		
//		byte[] bytes = null;
		Map parametros = new HashMap();
		try {
			
			String possuiGerente = request.getParameter("possuiGerente");
			String titulo = request.getParameter("titulo");
			String pagina = request.getParameter("pagina");
			String data = request.getParameter("data");			
			Integer paginaInt = null;
			
			logger.info("Imprimir gerente no relatorio? " + possuiGerente );
			
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
					titulo = "ESTADO DO PARAN� - PLANO PLURIANUAL 2008/2011 - Demonstrativo por Programa";	
				}
			} catch (Exception e) {
				titulo = "ESTADO DO PARAN� - PLANO PLURIANUAL 2008/2011 - Demonstrativo por Programa";
			}						
			
			
			final RelatorioPPALinhaAcaoService service = RelatorioPPALinhaAcaoService.getInstance(request);
						
			// path absoluto do relatorio PPA
			final String pathRelPPA = context.getRealPath( JasperService.PATH_RELATORIO + "/relatorioPPALinhaAcao.jasper" );
			final String pathRelPPACapa = context.getRealPath( JasperService.PATH_RELATORIO + "/relatorioPPA_Demonstrativos_capa.jasper" );			

			JasperReport relatorioJasper = JasperService.loadReport( pathRelPPA );
			JasperReport relatorioCapaJasper = JasperService.loadReport( pathRelPPACapa );			
			this.redirectDefaultErrorIfNull(request, response, relatorioJasper);
			
			ArrayList<PPA_LinhaAcaoBean> relatorioDS = service.generatePPA( Boolean.valueOf( possuiGerente ).booleanValue() );
			this.redirectDefaultErrorIfNull(request, response, relatorioDS);
			
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(relatorioDS);
			
			if (possuiGerente!=null && "true".equalsIgnoreCase(possuiGerente) ){
				parametros.put("coluna1", "Linha de A��o / Programa / Gerente" );
			}else{
				parametros.put("coluna1", "Linha de A��o / Programa" );
			}
			
			parametros.put("pagina", paginaInt );
			parametros.put( "data" , data );
			parametros.put( "titulo" , titulo );	
			parametros.put("totalExer1", service.getTotalGeral1() );
			parametros.put("totalExer2", service.getTotalGeral2() );
			parametros.put("totalExer3", service.getTotalGeral3() );
			parametros.put("totalExer4", service.getTotalGeral4() );
			parametros.put("totalGeral", service.getTotalGeral() );

			
			JasperPrint capa = JasperService.fillReport(relatorioCapaJasper,parametros );
			JasperPrint rel = JasperService.fillReport(relatorioJasper,parametros, ds);
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			JasperService.addRelatorio( capa, rel, out );			
			this.responseToPDF(response, out.toByteArray());
			
		} catch (Exception e) {
			logger.error( "Nao foi possivel gerar relatorio PPA por Linha de A��o",e);
		}
		
	}
	


	
}
