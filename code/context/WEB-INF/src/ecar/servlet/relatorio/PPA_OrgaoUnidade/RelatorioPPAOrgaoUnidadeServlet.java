package ecar.servlet.relatorio.PPA_OrgaoUnidade;

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

import ecar.servlet.relatorio.PPA_Orgao.PPA_OrgaoBean;
import ecar.util.jasper.JasperService;
import ecar.util.jasper.servlet.RelatorioServlet;

/**
 * Servlet responsavel por interagir com os eventos de geracao de relatorio PPA por Orgao / Unidade
 * @author gabrielsolana
 *
 */
public class RelatorioPPAOrgaoUnidadeServlet extends RelatorioServlet {
	

	private static final long serialVersionUID = 6365940120217655517L;
	private Logger logger = Logger.getLogger(this.getClass());

        /**
         *
         */
        public RelatorioPPAOrgaoUnidadeServlet(){
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

		logger.info("Generating relatorio PPA Por Orgao / Unidade...");
		
		final ServletContext context = getServletContext();
		
		byte[] bytes = null;
		Map parametros = new HashMap();
		try {
			
			String titulo = request.getParameter("titulo");
			String pagina = request.getParameter("pagina");
			String data = request.getParameter("data");			
			Integer paginaInt = null;
			
			final RelatorioPPAOrgaoUnidadeService service = RelatorioPPAOrgaoUnidadeService.getInstance(request);
						
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
					titulo = "ESTADO DO PARAN� - PLANO PLURIANUAL 2008/2011 - Demonstrativo por �rg�o e Administra��o Indireta";	
				}
			} catch (Exception e) {
				titulo = "ESTADO DO PARAN� - PLANO PLURIANUAL 2008/2011 - Demonstrativo por �rg�o e Administra��o Indireta";
			}	
			
			// path absoluto do relatorio PPA
			final String pathRelPPA = context.getRealPath( JasperService.PATH_RELATORIO + "/relatorioPPAOrgaoUnidade.jasper" );
			final String pathRelPPACapa = context.getRealPath( JasperService.PATH_RELATORIO + "/relatorioPPA_Demonstrativos_capa.jasper" );				

			JasperReport relatorioJasper = JasperService.loadReport( pathRelPPA );
			JasperReport relatorioCapaJasper = JasperService.loadReport( pathRelPPACapa );	
			this.redirectDefaultErrorIfNull(request, response, relatorioJasper);
			
			ArrayList<PPA_OrgaoBean> relatorioDS = service.generatePPA();
			this.redirectDefaultErrorIfNull(request, response, relatorioDS);
			
			parametros.put("pagina", paginaInt );
			parametros.put( "data" , data );
			parametros.put( "titulo" , titulo );
			parametros.put("totalExer1", service.getTotalGeral1() );
			parametros.put("totalExer2", service.getTotalGeral2() );
			parametros.put("totalExer3", service.getTotalGeral3() );
			parametros.put("totalExer4", service.getTotalGeral4() );
			parametros.put("totalGeral", service.getTotalGeral() );
			
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(relatorioDS);
			
			JasperPrint capa = JasperService.fillReport(relatorioCapaJasper,parametros );
			JasperPrint rel = JasperService.fillReport(relatorioJasper,parametros, ds);
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			JasperService.addRelatorio( capa, rel, out );			
			this.responseToPDF(response, out.toByteArray());
			
		} catch (Exception e) {
			logger.error( "Nao foi possivel gerar relatorio PPA por Orgao / Unidade",e);
		}
		
	}

	
}
